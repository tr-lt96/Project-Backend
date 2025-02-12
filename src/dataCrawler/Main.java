package dataCrawler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Main {
	private static final String FILE_NAME = "stock_prices.json"; // JSON file storage
	private static final LocalTime MARKET_OPEN = LocalTime.of(9, 30); // 9:30 AM EST
	private static final LocalTime MARKET_CLOSE = LocalTime.of(16, 0); // 4:00 PM EST

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Allow user to enter multiple stock symbols
		System.out.println("Enter stock symbols separated by commas (e.g., AAPL, GOOG, MSFT): ");
		String input = scanner.nextLine();
		String[] stockSymbols = input.split(",");

		// Trim whitespace and convert to uppercase
		for (int i = 0; i < stockSymbols.length; i++) {
			stockSymbols[i] = stockSymbols[i].trim().toUpperCase();
		}

		// Infinite loop to keep checking market status
		while (true) {
			if (isMarketOpen()) {
				System.out.println("Market is open. Starting data collection...");
				startDataCollection(stockSymbols);
			} else {
				System.out.println("Market is closed. Waiting for the next market open...");
				waitUntilMarketOpens();
			}
		}
	}

	private static boolean isMarketOpen() {
		LocalTime currentTime = LocalTime.now(ZoneId.of("America/New_York"));
		return currentTime.isAfter(MARKET_OPEN) && currentTime.isBefore(MARKET_CLOSE);
	}

	private static void waitUntilMarketOpens() {
		try {
			while (!isMarketOpen()) {
				Thread.sleep(60000); // Check every minute
			}
		} catch (InterruptedException e) {
			System.out.println("Error in waitUntilMarketOpens: " + e.getMessage());
		}
	}

	private static void startDataCollection(String[] stockSymbols) {
		// Initialize the JSON file
		initializeJsonFile(FILE_NAME);

		// Schedule the task every 15 minutes for each stock symbol
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(stockSymbols.length);
		for (String stockSymbol : stockSymbols) {
			scheduler.scheduleAtFixedRate(() -> {
				if (!isMarketOpen()) {
					System.out.println("Market closed. Stopping data collection...");
					scheduler.shutdown();
				} else {
					fetchAndStorePrice(stockSymbol);
				}
			}, 0, 15, TimeUnit.MINUTES);
		}

		// Wait until the market closes, then restart the loop
		while (isMarketOpen()) {
			try {
				Thread.sleep(60000); // Check every minute if the market is still open
			} catch (InterruptedException e) {
				System.out.println("Error in market monitoring: " + e.getMessage());
			}
		}

		System.out.println("Market closed. Preparing to restart when market opens.");
		scheduler.shutdown();
	}

	private static void fetchAndStorePrice(String stockSymbol) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			URL jsonUrl = new URL("https://query1.finance.yahoo.com/v8/finance/chart/" + stockSymbol);
			InputStream inputStream = jsonUrl.openStream();

			Root rootObject = mapper.readValue(inputStream, Root.class);

			// Extract real stock price without modification
			List<Double> prices = rootObject.getChart().getResult().get(0).getIndicators().getQuote().get(0).getClose();
			List<Long> timestamps = rootObject.getChart().getResult().get(0).getTimestamp();
			String stockName = rootObject.getChart().getResult().get(0).getMeta().getLongName();

			// Get the latest price and timestamp from Yahoo Finance API
			double latestPrice = prices.get(prices.size() - 1);
			long latestTimestamp = timestamps.get(timestamps.size() - 1);

			// Store data in JSON format
			appendToJson(FILE_NAME, stockName, stockSymbol, latestTimestamp, latestPrice);

			System.out.println("Fetched and stored data: name=" + stockName + ", symbol=" + stockSymbol + ", timestamp="
					+ latestTimestamp + ", price=" + latestPrice);

		} catch (Exception e) {
			System.out.println("Error fetching or storing data for " + stockSymbol + ": " + e.getMessage());
		}
	}

	private static void initializeJsonFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(file, new ArrayList<StockRecord>()); // Start with an empty JSON array
				System.out.println("JSON file initialized: " + fileName);
			} catch (IOException e) {
				System.out.println("Error initializing JSON file: " + e.getMessage());
			}
		}
	}

	private static void appendToJson(String fileName, String name, String symbol, long timestamp, double price) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<StockRecord> records;

			File file = new File(fileName);
			if (file.exists() && file.length() > 0) {
				// Read existing records
				records = mapper.readValue(file, new TypeReference<List<StockRecord>>() {
				});
			} else {
				records = new ArrayList<>();
			}

			// Append new record with the real timestamp and price
			records.add(new StockRecord(name, symbol, timestamp, price));

			// Write back to the file
			ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
			writer.writeValue(file, records);

		} catch (IOException e) {
			System.out.println("Error writing to JSON file: " + e.getMessage());
		}
	}
}
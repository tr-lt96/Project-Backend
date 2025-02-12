package CONNECT_DATABASE;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader {
	public ArrayList<StockRecord> readJson(String fileName) {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<StockRecord> stockRecords = new ArrayList<>();

		try {
			// Read JSON file into a List of StockRecord objects
			stockRecords = (ArrayList<StockRecord>) objectMapper.readValue(new File(fileName),
					new TypeReference<List<StockRecord>>() {
					});

			// Print the data
			for (StockRecord stock : stockRecords) {
				System.out.println("ID: " + (stockRecords.indexOf(stock) + 1) + ", Name: " + stock.getName()
						+ ", Symbol: " + stock.getSymbol() + ", Price: " + stock.getPrice() + ", Timestamp: "
						+ stock.getTimestamp());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return stockRecords; // Return the list instead of null
	}
}
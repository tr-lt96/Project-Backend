package CONNECT_DATABASE;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		// Create instances of JsonReader and StockDAO
		JsonReader jsonReader = new JsonReader();
		StockDAO stockDAO = new StockDAO();

		// Path to the existing JSON file
		String filePath = "stock_prices.json";

		// Read stock data from the JSON file
		ArrayList<StockRecord> stocks = jsonReader.readJson(filePath);

		// Insert each stock record into the database
		for (StockRecord stock : stocks) {
			stockDAO.insert(stock);
		}

		System.out.println("✅ All stock records inserted into the database!");
	}

}

package CONNECT_DATABASE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class StockDAO {
	public void insert(StockRecord stockRecord) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// Establish MySQL database connection
			connection = DBultility.makeConnection();

			// Correct SQL Query (Use `timestamp` column correctly)
			String SQL_QUERY = "INSERT INTO STOCK.stocks (id, name, symbol, timestamp, price) VALUES (?, ?, ?, ?, ?)";

			// Create PreparedStatement
			statement = connection.prepareStatement(SQL_QUERY);

			// Convert Unix timestamp to MySQL TIMESTAMP format
			Timestamp sqlTimestamp = new Timestamp(stockRecord.getTimestamp() * 1000L); // Convert seconds to
																						// milliseconds

			// Set values for each column
			statement.setInt(1, stockRecord.getId());
			statement.setString(2, stockRecord.getName());
			statement.setString(3, stockRecord.getSymbol());
			statement.setTimestamp(4, sqlTimestamp); // Use converted timestamp
			statement.setDouble(5, stockRecord.getPrice());

			// Execute the insertion
			statement.executeUpdate();
			System.out.println("✅ Stock inserted: " + stockRecord.getSymbol());

		} catch (Exception e) {
			System.out.println("❌ Error inserting stock: " + e.getMessage());
		} finally {
			// Close database connection
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				System.out.println("❌ Error closing database connection: " + e2.getMessage());
			}
		}
	}
}
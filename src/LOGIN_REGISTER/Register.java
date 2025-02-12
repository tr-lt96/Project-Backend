package LOGIN_REGISTER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import CONNECT_DATABASE.DBultility;

public class Register {

	public static void main(String[] args) {
		Register register = new Register();
		User newUser = register.userInsert("tranluat@example.com2", "abc123");

		if (newUser != null) {
			System.out.println("✅ User registered: " + newUser);
		} else {
			System.out.println("❌ Failed to register user!");
		}
	}

	public User userInsert(String email, String password) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;

		try {

			connection = DBultility.makeConnection();

			String SQL_QUERY = "INSERT INTO STOCK.user (email, password) VALUES (?, ?)";
			statement = connection.prepareStatement(SQL_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);

			statement.setString(1, email);
			statement.setString(2, password);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int userId = generatedKeys.getInt(1);
					return new User(userId, null, email, password);
				}
			}
		} catch (SQLException e) {
			System.out.println("❌ Error while creating user: " + e.getMessage());
		} finally {
			// Close resources
			try {
				if (generatedKeys != null)
					generatedKeys.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				System.out.println("⚠ Error closing resources: " + ex.getMessage());
			}
		}
		return null;
	}
}
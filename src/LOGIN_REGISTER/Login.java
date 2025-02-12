package LOGIN_REGISTER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import CONNECT_DATABASE.DBultility;

public class Login {

	public static void main(String[] args) {
		Login login = new Login();
		User verifyUser = login.loginCheck("tranluat@example.com100", "abc123");

		if (verifyUser != null) {
			System.out.println("✅ Login successful! Welcome,");
			// Send to the homepage
		} else {
			System.out.println("❌ Failed to login, please try again.");
		}
	}

	public User loginCheck(String email, String password) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DBultility.makeConnection();

			// Correct SQL Query
			String SQL_QUERY = "SELECT id, name,email, password FROM STOCK.user WHERE email = ? AND password = ?";

			statement = connection.prepareStatement(SQL_QUERY);
			statement.setString(1, email);
			statement.setString(2, password);

			resultSet = statement.executeQuery();

			// If user exists, return user object
			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String userName = resultSet.getString("name");
				String userEmail = resultSet.getString("email");
				String dbPassword = resultSet.getString("password");

				return new User(id, userName, userEmail, dbPassword); // ✅ Correct user object
			}

		} catch (SQLException e) {
			System.out.println("❌ Database error: " + e.getMessage());
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				System.out.println("⚠ Error closing resources: " + ex.getMessage());
			}
		}

		return null; // ❌ Return null if login fails
	}
}
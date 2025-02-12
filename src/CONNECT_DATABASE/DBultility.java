package CONNECT_DATABASE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBultility {

	public static Connection makeConnection() {
		String PATH = "jdbc:mysql://127.0.0.1:3306/Product?user=root&useSSL=false&serverTimezone=UTC";
		String userName = "root";
		String password = "17091996";

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(PATH, userName, password);
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

}
package edu.dal.eauction.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	private String jdbcURL = System.getenv("URL");
	private String username = System.getenv("USERNAME");
	private String password = System.getenv("PASSWORD");

	private static DBConnect dbconnect;

	private DBConnect() {
	}

	public static DBConnect getInstance() {
		if (dbconnect == null) {
			dbconnect = new DBConnect();
		}
		return dbconnect;
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
}

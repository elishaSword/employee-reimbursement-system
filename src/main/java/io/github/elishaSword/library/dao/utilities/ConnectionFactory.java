package io.github.elishaSword.library.dao.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static final String url = System.getenv("CAFE_DB_URL");
	private static final String username = System.getenv("CAFE_DB_USERNAME");
	private static final String password = System.getenv("CAFE_DB_PASSWORD");
	
	private ConnectionFactory() {
		super();
	}

	public static Connection get() {
		Connection connection;

		try {
			Class.forName("org.postgresql.Driver"); // why do i need this
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException | ClassNotFoundException e) {
			connection = null;
			e.printStackTrace();
		}
		
		return connection;
	}
}

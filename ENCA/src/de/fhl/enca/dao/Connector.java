package de.fhl.enca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class Connector
 * Class responible for connecting the database and
 * sending sql query to database
 */
public final class Connector {

	private static Connection connection;

	static {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + Connector.class.getResource("/data/data.db"));
		} catch (SQLException e) {
			e.printStackTrace();
			connection = null;
		}
	}

	private static Statement statement;

	static {
		if (connection != null) {
			try {
				statement = connection.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
				statement = null;
			}
		} else {
			statement = null;
		}
	}

	public static ResultSet executeSelect(String sql) {
		try {
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean executeNonSelect(String sql) {
		try {
			return statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
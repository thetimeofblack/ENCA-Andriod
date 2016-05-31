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

	private static final Connection CONNECTION = initConnection();

	private static Connection initConnection() {
		try {
			return DriverManager.getConnection("jdbc:sqlite:res\\data\\data.db");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static final Statement STATEMENT = initStatement();

	private static Statement initStatement() {
		if (CONNECTION != null) {
			try {
				return CONNECTION.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public static ResultSet executeSelect(String sql) {
		try {
			return STATEMENT.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean executeNonSelect(String sql) {
		try {
			return STATEMENT.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
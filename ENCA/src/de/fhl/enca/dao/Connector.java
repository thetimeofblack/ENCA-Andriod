package de.fhl.enca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class Connector {

	private static Connection connection = null;
	private static Statement statement = null;

	public static boolean Connect() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:res\\data\\data.db");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
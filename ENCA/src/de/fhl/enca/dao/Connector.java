package de.fhl.enca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Responsible for connecting the database and sending SQL query to database
 * @author Zhaowen.Gong, Zeling.Wu
 * @version 30.06.2016
 */
public final class Connector {

	/**
	 * The unique connection.
	 */
	private static Connection connection = null;

	/**
	 * Establish the connection.
	 */
	static {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + Connector.class.getResource("/data/data.db"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send select SQL query to the database.
	 * @param sql select SQL query
	 * @return the result get from the database
	 */
	public static ResultSet executeSelect(String sql) {
		try {
			return connection.createStatement().executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Send update, insert into and delete SQL query to the database.
	 * @param sql update, insert into and delete SQL query
	 */
	public static void executeNonSelect(String sql) {
		try {
			connection.createStatement().execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save Image into database
	 * @param sql update image, image to insert
	*/
	public static void executeImage(String sql, byte[] image){
		try{
			PreparedStatement pstmt=connection.prepareStatement(sql);
			pstmt.setBytes(1, image);
			pstmt.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
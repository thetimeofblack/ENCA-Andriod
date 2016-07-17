package de.fhl.enca.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.fhl.enca.bl.User;

/**
 * Responsible for connecting the database and sending SQL query to database
 * @author Zhaowen.Gong, Zeling.Wu
 * @version 30.06.2016
 */
public final class Connector {
	
	/**
	 * Database locations.
	 */
	private static final String DATABASE_NAME = "data.db";
	private static File target = new File(User.getDirectory(), DATABASE_NAME);

	/**
	 * The unique connection.
	 */
	private static Connection connection = null;

	/**
	 * Establish the connection.
	 */
	public static void connect() {
		/* Copy database if not exist */
		if (User.isFirstUse() || !target.exists()) {
			InputStream input = null;
			OutputStream output = null;
		    try {
		    	input = Connector.class.getResourceAsStream("/data/data.db");
		    	if(!User.getDirectory().exists()) {
		    		User.getDirectory().mkdirs();
		    	}
		        output = new FileOutputStream(target);
		        byte[] buf = new byte[1024];
		        int bytesRead;
		        while ((bytesRead = input.read(buf)) > 0) {
		            output.write(buf, 0, bytesRead);
		        }
		    } catch (Exception e) {
				e.printStackTrace();
			} finally {
		        try {
					input.close();
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		}
		/* Establish connection */
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + target.getAbsolutePath());
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
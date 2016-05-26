package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class TestField {

	public static void main(String[] args) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from student");
		while (resultSet.next()) {
			System.out.println(resultSet.getString(1));
		}
	}
}
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private static String url = "jdbc:mysql://localhost:3306/database";
	
	private static String usr = "root";
	
	private static String pasw = "ipsg1";
	
	
	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(url,usr,pasw);
	}   
	   
	   
}

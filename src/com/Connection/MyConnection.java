package com.Connection;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

// Connection Pool Setup
public class MyConnection
{
	private static String username = "library";
	private static String password = "library";
	private static String dbURL = "jdbc:mysql://localhost:3306/library";
	private static String driver = "com.mysql.jdbc.Driver";
	
	private static MyConnection instance;
	
	public static MyConnection getInstance()
	{
		if (instance == null)
		{
			instance = new MyConnection();
		}
		
		return instance;
	}
	
	public Connection getConnection() throws PropertyVetoException, SQLException
	{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		dataSource.setUser(username);
		dataSource.setPassword(password);
		dataSource.setJdbcUrl(dbURL);
		dataSource.setDriverClass(driver);

		return dataSource.getConnection();
	}
}

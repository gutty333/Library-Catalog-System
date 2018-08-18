package com.DAO;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Connection.MyConnection;
import com.Entity.Patron;

public class PatronDAO
{
	// method for retrieve a patron through email and password input fields
	public static Patron getPatron(String email, String password, MyConnection instance)
	{
		Patron current = null;
		
		try(Connection connection = instance.getConnection())
		{
			String query = "select * from patron where email=? and password=?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, email);
			statement.setString(2, password);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next())
			{
				int ID = resultSet.getInt("ID");
				String firstName = resultSet.getString("FirstName");
				String lastName = resultSet.getString("LastName");
				String currentEmail = resultSet.getString("Email");
				double fines = resultSet.getDouble("TotalFines");
				
				return new Patron(ID, firstName, lastName, currentEmail, fines);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		catch (PropertyVetoException e)
		{
			e.printStackTrace();
		}
		
		return current;
	}

	// method to check if an account already exist by analyzing the email
	public static boolean accountExist(String email, MyConnection instance)
	{
		try(Connection connection = instance.getConnection())
		{
			String query = "select * from patron where email=?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, email);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next())
			{
				return true;
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		catch (PropertyVetoException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

	// method for inserting a new patron into our database
	public static void insertPatron(Patron current, MyConnection instance)
	{
		try(Connection connection = instance.getConnection())
		{
			String query = "INSERT INTO patron (`FirstName`, `LastName`, `Email`, `Password`) VALUES (?, ?, ?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			// binding the parameters
			statement.setString(1, current.getFirstName());
			statement.setString(2, current.getLastName());
			statement.setString(3, current.getEmail());
			statement.setString(4, current.getPassword());
			
			statement.execute();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		catch (PropertyVetoException e)
		{
			e.printStackTrace();
		}
	}

	// method to reprieve a patron through their ID
	public static Patron getPatron(int id, MyConnection instance)
	{
		Patron current = null;
		
		try(Connection connection = instance.getConnection())
		{
			String query = "select * from patron where ID=?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next())
			{
				int ID = resultSet.getInt("ID");
				String firstName = resultSet.getString("FirstName");
				String lastName = resultSet.getString("LastName");
				String currentEmail = resultSet.getString("Email");
				double fines = resultSet.getDouble("TotalFines");
				
				return new Patron(ID, firstName, lastName, currentEmail, fines);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		catch (PropertyVetoException e)
		{
			e.printStackTrace();
		}
		
		return current;
	}

	// method charging the current patron with the calculated late fees
	public static void chargePatron(int patronID, double fine, MyConnection instance)
	{
		try(Connection connection = instance.getConnection())
		{
			String query = "UPDATE `patron` SET `TotalFines`=? WHERE `ID`=?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setDouble(1, fine);
			statement.setInt(2, patronID);
			
			statement.execute();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		catch (PropertyVetoException e)
		{
			e.printStackTrace();
		}
	}
}

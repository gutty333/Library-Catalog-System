package com.DAO;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.Connection.MyConnection;
import com.Entity.Book;

public class BookDAO
{
	// retrieve all the books
	public static List<Book> getBooks(MyConnection instance)
	{
		List<Book> bookList = new ArrayList<>();
		
		try(Connection connection = instance.getConnection())
		{
			String query = "select * from book";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next())
			{
				int id =  resultSet.getInt("ID");
				String title = resultSet.getString("Title");
				String author = resultSet.getString("Author");
				String genre = resultSet.getString("Genre");
				boolean available = resultSet.getBoolean("Available");;
				
				bookList.add(new Book(id, title, author, genre, available));
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
		
		return bookList;
	}

	// get books based on the provided filters
	public static List<Book> getBooks(String searchInput, String genre, MyConnection instance)
	{
		List<Book> bookList = new ArrayList<>();
		
		try(Connection connection = instance.getConnection())
		{
			// search query to handle patron's input
			String query = "SELECT * from book where (Title like ? or Author like ?) and Genre=?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			// making use of wildcards
			StringBuilder newSearch = new StringBuilder();
			newSearch.append("%");
			newSearch.append(searchInput);
			newSearch.append("%");
			
			// binding parameters
			statement.setString(1, newSearch.toString());
			statement.setString(2, newSearch.toString());
			statement.setString(3, genre);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next())
			{
				int id =  resultSet.getInt("ID");
				String title = resultSet.getString("Title");
				String author = resultSet.getString("Author");
				String currentGenre = resultSet.getString("Genre");
				boolean available = resultSet.getBoolean("Available");;
				
				bookList.add(new Book(id, title, author, currentGenre, available));
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
		
		return bookList;
	}

	// get books based on the genre only
	public static List<Book> getBooksGenre(String genre, MyConnection instance)
	{
		List<Book> bookList = new ArrayList<>();
		
		try(Connection connection = instance.getConnection())
		{
			// search query to handle patron's input
			String query = "SELECT * from book where genre=?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			// binding parameter
			statement.setString(1, genre);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next())
			{
				int id =  resultSet.getInt("ID");
				String title = resultSet.getString("Title");
				String author = resultSet.getString("Author");
				String currentGenre = resultSet.getString("Genre");
				boolean available = resultSet.getBoolean("Available");;
				
				bookList.add(new Book(id, title, author, currentGenre, available));
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
		
		return bookList;
	}
	
	// get books by title or author only
	public static List<Book> getBooks(String searchInput, MyConnection instance)
	{
		List<Book> bookList = new ArrayList<>();
		
		try(Connection connection = instance.getConnection())
		{
			// search query to handle patron's input
			String query = "SELECT * from book where Title like ? or Author like ?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			StringBuilder newSearch = new StringBuilder();
			newSearch.append("%");
			newSearch.append(searchInput);
			newSearch.append("%");
			
			// binding parameters
			statement.setString(1, newSearch.toString());
			statement.setString(2, newSearch.toString());
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next())
			{
				int id =  resultSet.getInt("ID");
				String title = resultSet.getString("Title");
				String author = resultSet.getString("Author");
				String currentGenre = resultSet.getString("Genre");
				boolean available = resultSet.getBoolean("Available");;
				
				bookList.add(new Book(id, title, author, currentGenre, available));
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
		
		return bookList;
	}

	// method to return a book based on the ID
	public static Book getBook(int bookID, MyConnection instance)
	{
		Book myBook =  null;
		
		try(Connection connection = instance.getConnection())
		{
			String query = "select * from book where ID=?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, bookID);
			
			ResultSet resultSet = statement.executeQuery();
			
			while  (resultSet.next())
			{
				int id = resultSet.getInt("ID");
				String title = resultSet.getString("Title");
				String author = resultSet.getString("Author");
				String genre = resultSet.getString("Genre");
				boolean available = resultSet.getBoolean("Available");
				
				return new Book(id, title, author, genre, available);
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
		
		return myBook;
	}

	// method to keep track of which patron has taken the specified book
	// makes a new pair between the patron and book
	public static void bookTakeOut(int patronID, int bookID, MyConnection instance)
	{
		try(Connection connection = instance.getConnection())
		{
			String query = "INSERT INTO patron_book (`PatronID`, `BookID`) VALUES (?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, patronID);
			statement.setInt(2, bookID);
			
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

	// method to signal that the book is available in the library after being returned
	public static void bookIsAvailable(int bookID, MyConnection instance)
	{
		try(Connection connection = instance.getConnection())
		{
			String query = "UPDATE book SET `Available`='1' WHERE `ID`=?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, bookID);
			
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
		
	// method to update the availability of a book when is taken out by a patron
	public static void bookNotAvailable(int bookID, MyConnection instance)
	{
		try(Connection connection = instance.getConnection())
		{
			String query = "UPDATE book SET `Available`='0' WHERE `ID`=?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, bookID);
			
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

	// method to return all the books checked out by the specified patron
	public static List<Book> myBooks(int patronID, MyConnection instance)
	{
		List<Book> myList = new ArrayList<>();
		
		try(Connection connection = instance.getConnection())
		{
			// query to only target the books this patron has 
			// we reference the pair table which stores a record of all activities
			String query = "SELECT * FROM book left outer join patron_book " + 
					"on patron_book.PatronID = ? where patron_book.BookID = library.book.ID;";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, patronID);
			
			ResultSet resultSet = statement.executeQuery();
			
			while  (resultSet.next())
			{
				int id = resultSet.getInt("ID");
				String title = resultSet.getString("Title");
				String author = resultSet.getString("Author");
				String genre = resultSet.getString("Genre");
				boolean available = resultSet.getBoolean("Available");
				
				myList.add(new Book(id, title, author, genre, available));
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
		
		return myList;
	}
	
	// method to return the date for when the patron checked out the specified book
	public static LocalDate getCheckOutDate(int patronID, int bookID, MyConnection instance)
	{
		LocalDate checkOutDate = null;
		
		try(Connection connection = instance.getConnection())
		{
			String query = "SELECT TakenOut FROM patron_book where PatronID = ? and BookID = ?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, patronID);
			statement.setInt(2, bookID);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next())
			{
				return resultSet.getDate("TakenOut").toLocalDate();
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
		
		return checkOutDate;
	}

	// method to delete the pair record once the patron returns the book
	public static void deleteRecord(int patronID, int bookID, MyConnection instance)
	{
		try(Connection connection = instance.getConnection())
		{
			// query to first get the pair ID
			String query = "SELECT * FROM patron_book WHERE patron_book.PatronID=? and patron_book.BookID=?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, patronID);
			statement.setInt(2, bookID);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				int pairID = resultSet.getInt("ID");
				
				// query to now delete the pair record
				query = "DELETE FROM `patron_book` WHERE `ID`=?";
				
				statement = connection.prepareStatement(query);
				
				statement.setInt(1, pairID);
				
				statement.execute();
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
	}
}

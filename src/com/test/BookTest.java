package com.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.Connection.MyConnection;
import com.DAO.BookDAO;
import com.Entity.Book;

public class BookTest
{
	private static MyConnection instance;
	
	@BeforeClass
	public static void beforeSetup()
	{
		instance = MyConnection.getInstance();
	}
	
	@Test
	public void shouldReturnAllBooks()
	{
		List<Book>bookList = BookDAO.getBooks(instance);
		
		int totalBooks = 52;
		
		assertEquals(totalBooks, bookList.size());
	}
	
	
	@Test
	public void shouldReturnCorrectAmountOfBooks()
	{
		List<Book> bookList = BookDAO.getBooks("en", "Fiction", instance);
		List<Book> bookList2 = BookDAO.getBooks("", "", instance);
		
		assertTrue(bookList.size() > 0);
		assertTrue(bookList2.size() == 0);
	}
	
	
	@Test
	public void shouldReturnBookByID()
	{
		Book valid = BookDAO.getBook(1, instance);
		Book invalid = BookDAO.getBook(-3, instance);
		
		assertTrue(valid != null);
		assertTrue(invalid == null);
	}
}

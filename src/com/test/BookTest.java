package com.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.DAO.BookDAO;
import com.Entity.Book;

public class BookTest
{
	@Test
	public void shouldReturnAllBooks()
	{
		List<Book>bookList = BookDAO.getBooks();
		
		int totalBooks = 52;
		
		assertEquals(totalBooks, bookList.size());
	}
	
	
	@Test
	public void shouldReturnCorrectAmountOfBooks()
	{
		List<Book> bookList = BookDAO.getBooks("en", "Fiction");
		List<Book> bookList2 = BookDAO.getBooks("", "");
		
		assertTrue(bookList.size() > 0);
		assertTrue(bookList2.size() == 0);
	}
	
	
	@Test
	public void shouldReturnBookByID()
	{
		Book valid = BookDAO.getBook(1);
		Book invalid = BookDAO.getBook(-3);
		
		assertTrue(valid != null);
		assertTrue(invalid == null);
	}
}

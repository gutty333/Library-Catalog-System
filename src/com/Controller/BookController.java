package com.Controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BookDAO;
import com.DAO.PatronDAO;
import com.Entity.Book;
import com.Entity.Patron;


@WebServlet("/BookController")
public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int DATE_POLICY = 7;
	private static final double LATE_FEE = .50;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String command = request.getParameter("command");
		
		// section to keep track of our current patron
		int patronID = Integer.parseInt(request.getParameter("patronID"));
		Patron currentPatron = PatronDAO.getPatron(patronID);
		
		
		if (command != null && command.equals("TAKE"))
		{
			// section to handle the take operation for a book
			bookInfo(request, response, currentPatron);
		}
		else if (command != null && command.equals("TAKEOUT"))
		{
			// section to assign the book to the user/patron
			takeOutBook(request, response, patronID, currentPatron);
		}
		else if (command != null && command.equals("MYBOOKS"))
		{
			// section to display the books taken out by user/patron
			patronBooks(request, response, patronID, currentPatron);
		}
		else if (command != null && command.equals("RETURN"))
		{
			// section to handle the return operation
			returnBook(request, response, patronID, currentPatron);
		}
		else
		{
			// section to handle catalog search
			searchCatalog(request, response, currentPatron);
		}
	}

	
	// method allowing the patron to return any of the books they are holding
	private void returnBook(HttpServletRequest request, HttpServletResponse response, int patronID,
			Patron currentPatron) throws ServletException, IOException
	{
		// need to calculate difference of the date the book is returned with the date it was taken out
		int bookID = Integer.parseInt(request.getParameter("bookID"));
		
		// return date
		LocalDate returnDate = LocalDate.now();
		
		// getting the check out date
		LocalDate checkOutDate = BookDAO.getCheckOutDate(patronID, bookID);
		
		String message = null;
		String message2 = null;
		
		if (checkOutDate != null)
		{
			// deleting the patron book pair record
			BookDAO.deleteRecord(patronID,bookID);
			
			// updating the book availability flag
			BookDAO.bookIsAvailable(bookID);
			
			// get the selected book
			Book returnedBook = BookDAO.getBook(bookID);
			
			// getting the difference between the 2 dates
			// recall a patron can only hold a material for 7 days at most
			int totalDays = 0;
			
			// we continue to subtract days until we reach the check out date
			while (returnDate.minusDays(totalDays).isAfter(checkOutDate))
			{
				totalDays++;
			}
			
			// output messages
			message = "The book " + returnedBook.getTitle() + " has been returned on " + 
						returnDate.getMonth().toString() + " " + returnDate.getDayOfMonth() + ", " 
						+ returnDate.getYear();
			
			// condition to check if the patron returned the material on time
			// if returned late they will be charge with a late fee based on the amount of dates late
			if (totalDays > DATE_POLICY)
			{
				double fine = (totalDays - DATE_POLICY) * LATE_FEE;
				
				double totalFines = fine + currentPatron.getTotalFines();
				
				// formatting the output for the page view
				DecimalFormat decimalPrecision = new DecimalFormat(".00");
				
				// charge the patron with the late fine
				PatronDAO.chargePatron(patronID, totalFines);
				
				message2 = "This book was returned " + (totalDays - DATE_POLICY) + " day/s late, you will be charged $" + decimalPrecision.format(fine);
			}
			else
			{
				message2 = "This book was returned on time";
			}
		}

		// get the updated list of books for the current patron
		List<Book> myBooks = BookDAO.myBooks(patronID);
		
		// setting the page attributes
		request.setAttribute("patron", currentPatron);
		request.setAttribute("books", myBooks);
		if (message != null)
		{
			request.setAttribute("message", message);
			request.setAttribute("message2", message2.toString());
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/myBooks.jsp");
		
		dispatcher.forward(request, response);
	}

	
	// method show casing the books on hold by the current patron
	private void patronBooks(HttpServletRequest request, HttpServletResponse response, int patronID,
			Patron currentPatron) throws ServletException, IOException
	{
		List<Book>bookList = BookDAO.myBooks(patronID);
		
		request.setAttribute("patron", currentPatron);
		request.setAttribute("books", bookList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/myBooks.jsp");
		
		dispatcher.forward(request, response);
	}

	
	// method handling the book take out process, we update its availability and track which patron will have the book
	private void takeOutBook(HttpServletRequest request, HttpServletResponse response, int patronID, Patron current)
			throws ServletException, IOException
	{
		int bookID = Integer.parseInt(request.getParameter("bookID"));
		
		// providing the book to the patron and saving the record
		BookDAO.bookTakeOut(patronID,bookID);
		
		// updating the availability for the current book
		BookDAO.bookNotAvailable(bookID);
		
		// back to catalog
		searchCatalog(request, response, current);
	}

	
	// method handling all the search functionalities
	private void searchCatalog(HttpServletRequest request, HttpServletResponse response, Patron current)
			throws ServletException, IOException
	{
		// getting our filters
		String searchInput = request.getParameter("search");
		String genre = request.getParameter("genre");
		
		// our book list
		List<Book>bookList = new ArrayList<>(); 
		
		// list of genre
		List<String> genreList = Book.getGenreList();
		
		if ((searchInput == null && genre == null) || (searchInput.isEmpty() && genre.equals("Select Genre")))
		{
			// show all the books
			bookList = BookDAO.getBooks();
		}
		else if (searchInput.length() > 0 && !genre.equals("Select Genre"))
		{
			// show books based on the filters provided
			bookList = BookDAO.getBooks(searchInput, genre);
		}
		else if (!genre.equals("Select Genre"))
		{
			// show books based on the genre 
			bookList = BookDAO.getBooksGenre(genre);
		}
		else if (searchInput.length() > 0)
		{
			// show books based on the search field provided
			bookList = BookDAO.getBooks(searchInput);
		}
		
		// adding the attributes
		request.setAttribute("patron", current);
		request.setAttribute("books", bookList);
		request.setAttribute("genreList", genreList);
		
		// page target
		RequestDispatcher dispatcher = request.getRequestDispatcher("/searchBooks.jsp");
		
		dispatcher.forward(request, response);
	}


	// method show casing the selected book and allowing the patron to take the book out
	private void bookInfo(HttpServletRequest request, HttpServletResponse response, Patron current)
			throws ServletException, IOException
	{
		// get the selected book
		int bookID = Integer.parseInt(request.getParameter("bookID"));
		Book selectedBook = BookDAO.getBook(bookID);
		
		// setting up the return date
		// note all books must be returned within 7 days after their check out date
		LocalDate returnDate = LocalDate.now().plusDays(DATE_POLICY);
		
		// adding the attribute for the page
		request.setAttribute("patron", current);
		request.setAttribute("book", selectedBook);
		request.setAttribute("date", returnDate);
		
		// target page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/book.jsp");
		
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}
}

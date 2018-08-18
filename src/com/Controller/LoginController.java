package com.Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Connection.MyConnection;
import com.DAO.PatronDAO;
import com.Entity.Patron;


@WebServlet("/LoginController")
public class LoginController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private static String email;
	private static String password;
	private static String error;
	
	private MyConnection instance;
	
	// default constructor
	public LoginController()
	{
		instance = MyConnection.getInstance();
	}
	
	// get method
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String command = request.getParameter("command");
		
		if (command.equals("PASS"))
		{
			// getting the patron data from database
			Patron current = PatronDAO.getPatron(email, password, instance);
			
			// setting up our patron model
			request.setAttribute("patron", current);
			
			// selecting the page
			RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
			
			// sending the model
			dispatcher.forward(request, response);
		}
		else if (command.equals("FAIL"))
		{
			request.setAttribute("error", error);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			
			dispatcher.forward(request, response);
		}
	}


	// post method
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// getting the user's input
		email = request.getParameter("email");
		password = request.getParameter("password");
	
		// input validation
		if (email.isEmpty() || password.isEmpty())
		{
			// error when empty fields are present
			error = "Please fill out all the fields";
			sendError(error, request, response);
		}
		else if (PatronDAO.getPatron(email, password, instance) == null)
		{
			// error when invalid email or password is entered
			error = "Invalid email or password";
			sendError(error,request, response);
		}
		else 
		{
			// valid input fields
			response.sendRedirect(request.getContextPath()+"/LoginController?command=PASS");
		}
	}


	// method to handle input errors in the login form
	private void sendError(String errorMessage, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		response.sendRedirect(request.getContextPath()+"/LoginController?command=FAIL");
	}
}

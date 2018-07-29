package com.Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.PatronDAO;
import com.Entity.Patron;


@WebServlet("/SignupController")
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static String error;
	private static String firstName;
	private static String lastName;
	private static String email;
	private static String password;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String command = request.getParameter("command");
		
		if (command.equals("PASS"))
		{
			// our current patron info
			Patron current = new Patron();
			current.setFirstName(firstName);
			current.setLastName(lastName);
			current.setEmail(email);
			current.setPassword(password);
			
			// inserting the current patron into our database
			PatronDAO.insertPatron(current);
			
			current = PatronDAO.getPatron(email, password);
			
			// adding it as an attribute
			request.setAttribute("patron", current);
			
			// selecting the page
			RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
			
			// sending the model
			dispatcher.forward(request, response);
		}
		else if (command.equals("FAIL"))
		{
			request.setAttribute("error", error);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/signup.jsp");
			
			dispatcher.forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// getting the user's input
		firstName = request.getParameter("firstName");
		lastName = request.getParameter("lastName");
		email = request.getParameter("email");
		password = request.getParameter("password");
	
		// input validation
		if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty())
		{
			// error when empty fields are present
			error = "Please fill out all the fields";
			sendError(error, request, response);
		}
		else if (PatronDAO.accountExist(email))
		{
			// error when an already existing email is provided during account creation
			error = "An account already exist with that email";
			sendError(error,request, response);
		}
		else 
		{
			// valid input fields
			response.sendRedirect(request.getContextPath()+"/SignupController?command=PASS");
		}
	}


	private void sendError(String errorMessage, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.sendRedirect(request.getContextPath()+"/SignupController?command=FAIL");
	}
}

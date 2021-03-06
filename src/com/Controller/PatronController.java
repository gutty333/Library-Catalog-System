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


@WebServlet("/PatronController")
public class PatronController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MyConnection instance;
	
	// default constructor
	public PatronController()
	{
		instance = MyConnection.getInstance();
	}
	
	// get method
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// getting current patron ID
		int id = Integer.parseInt(request.getParameter("patronID"));
		
		Patron current = PatronDAO.getPatron(id, instance);
		
		request.setAttribute("patron", current);
		
		// target page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
		
		dispatcher.forward(request, response);
	}

	// post method
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}
}

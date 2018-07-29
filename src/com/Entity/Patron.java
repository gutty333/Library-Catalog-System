package com.Entity;

public class Patron
{
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private double totalFines;
	
	// default constructor
	public Patron(){}
	
	// overload constructor
	public Patron(int id, String firstName, String lastName, String email, double totalFines)
	{
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.totalFines = totalFines;
	}

	// getters and setters
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public double getTotalFines()
	{
		return totalFines;
	}

	public void setTotalFines(double totalFines)
	{
		this.totalFines = totalFines;
	}
	
	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}

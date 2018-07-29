package com.Entity;

import java.util.ArrayList;
import java.util.List;

public class Book
{
	private int id;
	private String title;
	private String author;
	private String genre;
	private boolean available;
	
	// constructor
	public Book(int id, String title, String author, String genre, boolean available)
	{
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.available = available;
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

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	public boolean isAvailable()
	{
		return available;
	}

	public void setAvailable(boolean available)
	{
		this.available = available;
	}
	
	// method to get the list of book genre
	public static List<String> getGenreList()
	{
		List<String> genreList = new ArrayList<>();
		genreList.add("Fiction");
		genreList.add("Classics");
		genreList.add("Mystery");
		genreList.add("European Literature");
		genreList.add("Fantasy");
		genreList.add("Nonfiction");
		genreList.add("Humor");
		genreList.add("Romance");
		genreList.add("Religion");
		genreList.add("Historical");
		
		return genreList;
	}
}

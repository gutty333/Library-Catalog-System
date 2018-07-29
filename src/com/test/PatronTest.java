package com.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.DAO.PatronDAO;
import com.Entity.Patron;

public class PatronTest
{
	@Test
	public void shouldReturnCorrectPatronAccount()
	{
		Patron valid = PatronDAO.getPatron("test", "test");
		Patron invalid = PatronDAO.getPatron("notvalid", "nothing");
		
		assertTrue(valid != null);
		assertTrue(invalid == null);
	}
	
	
	@Test
	public void shouldReturnPatronByID()
	{
		Patron valid = PatronDAO.getPatron(1);
		Patron invalid = PatronDAO.getPatron(300);
		
		assertTrue(valid != null);
		assertTrue(invalid == null);
	}
	
	
	@Test
	public void shouldCorrectlyChargeAPatron()
	{
		// first we will get data from a record
		Patron temp = PatronDAO.getPatron(1);
		
		temp.setTotalFines(0);
		double totalFines = temp.getTotalFines();
		double charge = 2.50;
		double expected = totalFines + charge;
		
		// charging the patron
		PatronDAO.chargePatron(1, charge);
		
		// getting the updated object
		temp = PatronDAO.getPatron(1);
		
		assertEquals(expected, temp.getTotalFines(), 2);
	}
}

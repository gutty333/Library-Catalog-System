package com.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.Connection.MyConnection;
import com.DAO.PatronDAO;
import com.Entity.Patron;

public class PatronTest
{
	private static MyConnection instance;
	
	@BeforeClass
	public static void beforeSetup()
	{
		instance = MyConnection.getInstance();
	}
	
	@Test
	public void shouldReturnCorrectPatronAccount()
	{
		Patron valid = PatronDAO.getPatron("test", "test", instance);
		Patron invalid = PatronDAO.getPatron("notvalid", "nothing", instance);
		
		assertTrue(valid != null);
		assertTrue(invalid == null);
	}
	
	
	@Test
	public void shouldReturnPatronByID()
	{
		Patron valid = PatronDAO.getPatron(1, instance);
		Patron invalid = PatronDAO.getPatron(300, instance);
		
		assertTrue(valid != null);
		assertTrue(invalid == null);
	}
	
	
	@Test
	public void shouldCorrectlyChargeAPatron()
	{
		// first we will get data from a record
		Patron temp = PatronDAO.getPatron(1, instance);
		
		temp.setTotalFines(0);
		double totalFines = temp.getTotalFines();
		double charge = 2.50;
		double expected = totalFines + charge;
		
		// charging the patron
		PatronDAO.chargePatron(1, charge, instance);
		
		// getting the updated object
		temp = PatronDAO.getPatron(1, instance);
		
		assertEquals(expected, temp.getTotalFines(), 2);
	}
}

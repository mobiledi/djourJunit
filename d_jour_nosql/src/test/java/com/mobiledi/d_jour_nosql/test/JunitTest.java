package com.mobiledi.d_jour_nosql.test;

import java.sql.Connection;
import java.sql.DriverManager;

import junit.framework.TestCase;

public class JunitTest extends TestCase {
	
	final static String DATABASE = "djour";
	final static String DB_USERNAME = "praks";
	final static String DB_PASSWORD = "";
	final static String DB_IP = "127.0.0.1:5432";
	final static String URL = "jdbc:postgresql://" + DB_IP + "/" + DATABASE;
	Connection connection = null;
	
	
	
	protected static void setUpBeforeClass() throws Exception {
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testsayHello() {

		System.out.println("Hello Testers from Jnuit");
		assertTrue(true);
	}

	public void testApp() {
		assertTrue(true);
	}

	public void testDBConnection() {
		
		boolean connected=connectToDB();
		assertEquals(true, connected);

	}
	
	
	private boolean connectToDB() {
		try {
			connection = DriverManager.getConnection(URL, DB_USERNAME,
					DB_PASSWORD);

			if (connection == null) {
				System.out.println("FAILED TO CONNECT TO POSTGRES");
				return false;
			}

			else {
				System.out.println("Connected to POSTGRES db "
						+ connection.toString());
				return true;
			}

		} catch (Exception e) {
			return false;
		}

	}
	

}

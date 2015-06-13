package com.mobiledi.d_jour_nosql.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import com.mobiledi.d_jour_nosql.Constants;

import junit.framework.TestCase;

public class JunitTest extends TestCase {
	
	final static String DATABASE = "djour";
	final static String DB_USERNAME = "praks";
	final static String DB_PASSWORD = "";
	final static String DB_IP = "127.0.0.1:5432";
	final static String URL = "jdbc:postgresql://" + DB_IP + "/" + DATABASE;
	Connection connection = null;
	
	final static String USER_USERNAME = "cp@gmail.com";
	final static String USER_PASSWORD = "login123";
	
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
	
	/*Check if a user is registered in the db*/
	public void testUserRegisteredinportal() {
		ObjectNode obj= new ObjectNode(JsonNodeFactory.instance);
		obj.put("username", USER_USERNAME);
		obj.put("password", USER_PASSWORD);
		 JsonNode toAuthenticate=obj;
		
		
	if (connectToDB()) {	
		String username = toAuthenticate.get(Constants.USERNAME).asText();
		String password = toAuthenticate.get(Constants.PASSWORD).asText();
		
		StringBuilder masterID = new StringBuilder();
		masterID.append("SELECT * FROM ");
		masterID.append(Constants.TABLE_RESTAURANT_MASTER);
		masterID.append(" WHERE " + Constants.COLUMN_EMAIL + "= '"+ username +"' AND " +Constants.COLUMN_PASSWORD + "='"+ password+"'");
		System.out.println("GENERATED SELECT QUERY: " + masterID.toString());
		try {
			PreparedStatement masterid = connection
					.prepareStatement(masterID.toString());
			
			ResultSet results=masterid.executeQuery();
			assertEquals("User is registered:", true, results.next());
			results.close();
			
		}
		catch(SQLException e){
			
			e.printStackTrace();
		}
		
		disconnectFromDB();
		
	}
	else{
		
		assertNotNull("Can't connect to db", connection);
	}
	
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
				System.out.println("Connected to POSTGRES db ");
				return true;
			}

		} catch (Exception e) {
			return false;
		}

	}
	
	private void disconnectFromDB() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}

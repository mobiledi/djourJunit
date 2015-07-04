package com.mobiledi.d_jour_nosql.test;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobiledi.djourDAO.DjourAppDAO;
import com.mobiledi.djourDAO.RestaurantManagerDAO;

import junit.framework.TestCase;

public class JunitTest extends TestCase {

	
	private static final String APP_USER_USERNAME = "pp";
	private static final String APP_USER_PASSWORD = "kk";
	
	
	private static final String PORTAL_USER_USERNAME = "cp@gmail.com";
	private static final String PORTAL_USER_PASSWORD = "login123";
	
	private static final String NAME = "Old Blinking Light";
	private static final String TITLE = "Seasonal, Southwestern-inspired American fare";
	private static final String EMAIL =  "www.oldblinkinglight.com";
	private static final String PHONE = "3033469797";
	private static final String WEBSITE = "www.oldblinkinglight.com";
	private static final String ADD_1 = "9344 Dorchester Street";
	private static final String ADD_2 = null;
	private static final String CITY = "Highlands Ranch";
	private static final String STATE = "CO";
	private static final int ZIP = 80129;
//HOURS TABLE
	private static final int WDOH = 8;
	private static final int WEOH = 8;
	private static final int WDOM = 30;
	private static final int WEOM = 00;
	private static final int WDCH = 20;
	private static final int WECH = 22;
	private static final int WDCM = 30;
	private static final int WECM = 00;
	
	
	RestaurantManagerDAO djourPortal;
	DjourAppDAO djourApp;
	
	protected static void setUpBeforeClass() throws Exception {
	}

	protected void setUp() throws Exception {
		super.setUp();
		djourPortal= new RestaurantManagerDAO();
		djourApp=new DjourAppDAO();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testApp() {
		assertTrue(true);
	}
/*	public void testGetUserDetails(){
		//ObjectNode obj= new ObjectNode(JsonNodeFactory.instance);
		//obj.put("username", PORTAL_USER_USERNAME);
		//obj.put("password", PORTAL_USER_PASSWORD);
		JsonNode result=djourPortal.getRestaurants();
		System.out.println("Restaurants info:" + result.toString());
		assertNotNull("Can't connect to db", result);
			
		}
	
	public void testRestaurantDetails(){
		int id=1;
		//ObjectNode obj= new ObjectNode(JsonNodeFactory.instance);
		//obj.put("username", PORTAL_USER_USERNAME);
		//obj.put("password", PORTAL_USER_PASSWORD);
		JsonNode result=djourPortal.getRestaurantProfile(id);
		System.out.println("Restaurants Profile for ID: "+id + " -- "  + result.toString());
		assertNotNull("Can't connect to db", result);
			
		}*/
	
	
/*
	public void testDBConnection() {	
		boolean connected=djour.connectToDB();
		assertEquals(true, connected);
	}*/
	
	/*Check if a user is registered in the db*/
/*public void testUserRegisteredinportal() {
		ObjectNode obj= new ObjectNode(JsonNodeFactory.instance);
		obj.put("username", PORTAL_USER_USERNAME);
		obj.put("password", PORTAL_USER_PASSWORD);
		JsonNode toAuthenticate=obj;
		assertEquals("User is registered:", true, djourPortal.isUserRegisteredinportal(toAuthenticate));
	}
	*/
	
/*public void testGetUserDetails(){
	ObjectNode obj= new ObjectNode(JsonNodeFactory.instance);
	obj.put("username", PORTAL_USER_USERNAME);
	obj.put("password", PORTAL_USER_PASSWORD);
	JsonNode result=djourPortal.getUserDetails(obj);
	System.out.println("User info:" + result.toString());
	assertNotNull("Can't connect to db", result);
		
	}*/

/*public void testUserRegisteredinApp() {
	assertTrue("User is registered:", djourApp.isUserRegisteredinApp(APP_USER_USERNAME, APP_USER_PASSWORD));

}*/


/*public void testAppSignUp(){
	ObjectNode testInput= new ObjectNode(JsonNodeFactory.instance);
	testInput.put("fname", NAME);
	testInput.put("lname", TITLE);
	testInput.put("sex", EMAIL);
	testInput.put("dob", PHONE);
	testInput.put("password", APP_USER_PASSWORD);
	assertEquals("APp user Signup was not successful", true, djourApp.persistAppUser(testInput));
	
	
	
}*/

/*public void testSignUp(){
	ObjectNode testInput= new ObjectNode(JsonNodeFactory.instance);
	testInput.put("name", NAME);
	testInput.put("title", TITLE);
	testInput.put("email", EMAIL);
	testInput.put("phone", PHONE);
	testInput.put("website", WEBSITE);
	testInput.put("password", USER_PASSWORD);
	testInput.put("address_line1", ADD_1);
	testInput.put("address_line2", ADD_2);
	testInput.put("city", CITY);
	testInput.put("state", STATE);
	testInput.put("zip", ZIP);
	assertEquals("Signup not successful", true, djour.persistSignUpData(testInput));
	
	
	
}
	public void testProfile(){
		ObjectNode testInput= new ObjectNode(JsonNodeFactory.instance);
		
		testInput.put("username", EMAIL);
		testInput.put("name", NAME);
		testInput.put("title", TITLE);
		testInput.put("email", EMAIL);
		testInput.put("phone", PHONE);
		testInput.put("website", WEBSITE);
		testInput.put("password", USER_PASSWORD);
		testInput.put("address_line1", ADD_1);
		testInput.put("address_line2", ADD_2);
		testInput.put("city", CITY);
		testInput.put("state", STATE);
		testInput.put("zip", ZIP);
		testInput.put("weekday_opening_hour", WDOH);
		testInput.put("weekend_opening_hour", WEOH);
		
		testInput.put("weekday_opening_minutes", WDOM);
		testInput.put("weekend_opening_minutes", WEOM);
		
		testInput.put("weekday_closing_hour", WDCH);
		testInput.put("weekend_closing_hour", WECH);
		
		testInput.put("weekday_closing_minutes", WDCM);
		testInput.put("weekend_closing_minutes", WECM);
		assertEquals("Update not successful", true, djour.updateSignUpData(testInput));
		
		
		
	}
*/

}

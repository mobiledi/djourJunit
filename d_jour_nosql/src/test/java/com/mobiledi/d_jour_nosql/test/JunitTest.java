package com.mobiledi.d_jour_nosql.test;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import com.mobiledi.d_jour_nosql.DjourPortalService;

import junit.framework.TestCase;

public class JunitTest extends TestCase {
	private static final String USER_USERNAME = "www.oldblinkinglight.com";//"cp@gmail.com";
	private static final String USER_PASSWORD = "login123";
	
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
	
	DjourPortalService djour;
	
	protected static void setUpBeforeClass() throws Exception {
	}

	protected void setUp() throws Exception {
		super.setUp();
		 djour= new DjourPortalService();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testApp() {
		assertTrue(true);
	}

	public void testDBConnection() {	
		boolean connected=djour.connectToDB();
		assertEquals(true, connected);
	}
	
	/*Check if a user is registered in the db*/
	public void testUserRegisteredinportal() {
		ObjectNode obj= new ObjectNode(JsonNodeFactory.instance);
		obj.put("username", USER_USERNAME);
		obj.put("password", USER_PASSWORD);
		JsonNode toAuthenticate=obj;
		assertEquals("User is registered:", true, djour.isUserRegisteredinportal(toAuthenticate));
	}
	
	
public void testGetUserDetails(){
	ObjectNode obj= new ObjectNode(JsonNodeFactory.instance);
	obj.put("username", USER_USERNAME);
	obj.put("password", USER_PASSWORD);
	JsonNode result=djour.getUserDetails(obj);
	System.out.println("User info:" + result.toString());
	assertNotNull("Can't connect to db", result);
		
	}

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
	
	
	
}*/

}

package com.mobiledi.djourrs.rmanagerrs.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Test;

public class TestGetRestaurantLists {

	final static Logger logger = Logger.getLogger(TestGetRestaurantLists.class);
	@Test
	public void getRestaurantListResponse() {
		String link = "http://localhost:8080/d_jour_nosql/restservice/api/portal/getallrestaurants";
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(link);
		try {
			get.addHeader("Accept", "application/json");
			HttpResponse response = client.execute(get);
			int status = response.getStatusLine().getStatusCode();
			logger.info("response code : "+ status);
			assertEquals(200, status);
			
			//  checking return data
			InputStream inputStream = new URL(link).openStream();
			String convertTOstring = convertStreamToString(inputStream);
			logger.info("convertStrign :::::"+convertTOstring);
			assertEquals("[{\"id\":13,\"name\":\"ashish_managerRs\",\"title\":\"ASHaa_RS\",\"email\":\"ashish@gmail.com\",\"banner_image\":null,\"phone\":\"111111\",\"website\":\"www.ashaa.com\",\"address_line1\":\"power grid colony\",\"address_line2\":\"lodhaee shamshabad\",\"city\":\"Agra\",\"state\":\"uttar pradesh\",\"zip\":282001,\"lat\":27.1407174,\"long\":78.0309542,\"weekday_opening_hour\":8,\"weekend_opening_hour\":10,\"weekday_opening_minutes\":30,\"weekend_opening_minutes\":15,\"weekday_closing_hour\":9,\"weekend_closing_hour\":7,\"weekday_closing_minutes\":45,\"weekend_closing_minutes\":35,\"org\":false,\"gf\":false,\"veg\":true,\"vgn\":false},{\"id\":14,\"name\":\"mradul_managerRs\",\"title\":\"mradulKum_RS\",\"email\":\"mradul@gmail.com\",\"banner_image\":null,\"phone\":\"98084\",\"website\":\"www.mradulkumar.com\",\"address_line1\":\"power grid colony\",\"address_line2\":\"lodhaee shamshabad\",\"city\":\"Agra\",\"state\":\"uttar pradesh\",\"zip\":282001,\"lat\":27.1407174,\"long\":78.0309542,\"weekday_opening_hour\":10,\"weekend_opening_hour\":9,\"weekday_opening_minutes\":10,\"weekend_opening_minutes\":9,\"weekday_closing_hour\":8,\"weekend_closing_hour\":7,\"weekday_closing_minutes\":8,\"weekend_closing_minutes\":35,\"org\":true,\"gf\":true,\"veg\":false,\"vgn\":true}]",convertTOstring);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}

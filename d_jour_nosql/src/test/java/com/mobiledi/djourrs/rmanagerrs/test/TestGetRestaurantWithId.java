package com.mobiledi.djourrs.rmanagerrs.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.print.DocFlavor.INPUT_STREAM;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestGetRestaurantWithId {

	private static Logger logger = LoggerFactory.getLogger(TestGetRestaurantWithId.class);
	@Test
	public void getRestaurantWithIdResponse() {
		String link = "http://localhost:8080/d_jour_nosql/restservice/api/portal/getrestaurant/14";
		HttpClient client = new DefaultHttpClient();
		HttpGet put = new HttpGet(link);
		try {
			put.addHeader("Accept", "application/json");
			HttpResponse response = client.execute(put);
			int status = response.getStatusLine().getStatusCode();
			logger.info("response code : "+ status);
			assertEquals(200, status);
			
			//  checking return data
			InputStream inputStream = new URL(link).openStream();
			String convertTOstring = convertStreamToString(inputStream);
			logger.info("convertStrign :::::"+convertTOstring);
			assertEquals("{\"id\":14,\"name\":\"mradul_managerRs\",\"title\":\"mradulKum_RS\",\"email\":\"mradul@gmail.com\",\"banner_image\":null,\"phone\":\"98084\",\"website\":\"www.mradulkumar.com\",\"address_line1\":\"power grid colony\",\"address_line2\":\"lodhaee shamshabad\",\"city\":\"Agra\",\"state\":\"uttar pradesh\",\"zip\":282001,\"lat\":27.1407174,\"long\":78.0309542,\"weekday_opening_hour\":10,\"weekend_opening_hour\":9,\"weekday_opening_minutes\":10,\"weekend_opening_minutes\":9,\"weekday_closing_hour\":8,\"weekend_closing_hour\":7,\"weekday_closing_minutes\":8,\"weekend_closing_minutes\":35,\"org\":true,\"gf\":true,\"veg\":false,\"vgn\":true}",convertTOstring);
			
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
	
	@SuppressWarnings("resource")
	public String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}

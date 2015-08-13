package com.mobiledi.djourrs.djourapprs.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import sun.net.www.protocol.http.HttpURLConnection;
import org.junit.Test;

public class TestAuthenticateAppUserDetails {

	@SuppressWarnings("restriction")
	@Test
	public void authUserDetailRespone() {
		String link = "http://localhost:8080/d_jour_nosql/restservice/api/appauthenticate/mradul/1111";		
		URL url;
		try {
			url = new URL(link);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			int status = connection.getResponseCode();
			assertEquals(200, status);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}

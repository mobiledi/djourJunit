package com.mobiledi.djourrs.djourapprs.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

public class TestHello {

	@Test
	public void helloMsg() {
		String link = "http://localhost:8080/d_jour_nosql/restservice/api/sayhello/modile_di ";
		InputStream inputStream;
		try {
			inputStream = new URL(link).openStream();
			String convertTOstring = convertStreamToString(inputStream);
			assertEquals("D'jour says Hello to modile_dinew data !",convertTOstring);
		
		} catch (MalformedURLException e) {
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

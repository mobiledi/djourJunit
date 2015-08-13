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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestGetRestaurantProfile {

	private static Logger logger = LoggerFactory.getLogger(TestGetRestaurantProfile.class);
	@Test
	public void getRestaurantProfileRespone() {
		String link = "http://localhost:8080/d_jour_nosql/restservice/api/portal/getrestaurantprofile";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(link);
		try {
			StringEntity entity = new StringEntity("{\"master_id\":14}");
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(entity);
			post.addHeader("Content-type", "application/json");
			HttpResponse response = client.execute(post);
			int status = response.getStatusLine().getStatusCode();
			logger.info("response code : "+ status);
			assertEquals(200, status);
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

}

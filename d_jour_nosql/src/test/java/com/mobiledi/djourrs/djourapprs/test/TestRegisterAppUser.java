package com.mobiledi.djourrs.djourapprs.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.junit.Test;

import com.mobiledi.djourDAO.DjourAppDAO;
import com.mobiledi.djourRS.DjourAppRS;

public class TestRegisterAppUser {
	
	@Test 
	public void registerAppUserResponse() {
		
		String link = "http://localhost:8080/d_jour_nosql/restservice/api/registerappuse";		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(link);
		try {
			StringEntity entity = new StringEntity("{\"username\":\"TITU\",\"password\": \"3333\"}");
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			httpPost.addHeader("Accept", "application/json");
			//httpPost.addHeader("Content-type", "application/json");
			httpPost.addHeader("Content-Length","17");
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int status = httpResponse.getStatusLine().getStatusCode();
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

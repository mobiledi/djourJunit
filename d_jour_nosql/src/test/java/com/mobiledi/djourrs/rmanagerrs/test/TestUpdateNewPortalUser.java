package com.mobiledi.djourrs.rmanagerrs.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUpdateNewPortalUser {

	private static Logger logger = LoggerFactory.getLogger(TestUpdateNewPortalUser.class);
	@Test
	public void updateNewPortalUserRespone() {
		String link = "http://localhost:8080/d_jour_nosql/restservice/api/portal/updatenewrestaurant";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(link);
		StringEntity entity;
		try {
			entity = new StringEntity("{\"email\":\"mradul@gmail.com\",\"master_id\":14,\"name\":\"mradul_managerRs\",\"banner_image\":\"102\",\"phone\":333,\"website\":\"www.mradulk.com\",\"title\":\"mradulK_RS\",\"address_line1\":\"power grid colony\",\"address_line2\":\"lodhaee shamshabad\",\"city\":\"Agra\",\"state\":\"uttar pradesh\",\"zip\":282001,\"weekday_opening_hour\":8,\"weekday_opening_minutes\":30,\"weekday_closing_hour\":9,\"weekday_closing_minutes\":45,\"weekend_opening_hour\":10,\"weekend_opening_minutes\":15,\"weekend_closing_hour\":7,\"weekend_closing_minutes\":35,\"org\":false,\"gf\":false,\"veg\":true,\"vgn\":false}");
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(entity);
			post.addHeader("Accept", "application/json");
			post.addHeader("Content-type", "application/json");
			
			HttpResponse response = client.execute(post);
			int status = response.getStatusLine().getStatusCode();
			logger.info("response code : "+ status);
			assertEquals(201, status);
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

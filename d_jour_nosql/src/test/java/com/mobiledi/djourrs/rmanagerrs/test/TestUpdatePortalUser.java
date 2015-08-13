package com.mobiledi.djourrs.rmanagerrs.test;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class TestUpdatePortalUser {
	private static Logger logger = LoggerFactory.getLogger(TestUpdatePortalUser.class);
	@Test
	public void updatePortalUserResponse() {
		String link = "http://localhost:8080/d_jour_nosql/restservice/api/portal/updaterestaurant";
		HttpClient client = new DefaultHttpClient();
		HttpPut put = new HttpPut(link);
		StringEntity entity;
		try {
			entity = new StringEntity("{\"email\":\"mradul@gmail.com\",\"master_id\":14,\"name\":\"mradul_managerRs\",\"banner_image\":\"102\",\"phone\":98084,\"website\":\"www.mradulkumar.com\",\"title\":\"mradulKum_RS\",\"address_line1\":\"power grid colony\",\"address_line2\":\"lodhaee shamshabad\",\"city\":\"Agra\",\"state\":\"uttar pradesh\",\"zip\":282001,\"weekday_opening_hour\":10,\"weekday_opening_minutes\":10,\"weekday_closing_hour\":8,\"weekday_closing_minutes\":8,\"weekend_opening_hour\":9,\"weekend_opening_minutes\":9,\"weekend_closing_hour\":7,\"weekend_closing_minutes\":35,\"org\":true,\"gf\":true,\"veg\":false,\"vgn\":true}");
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			put.setEntity(entity);
			put.addHeader("Accept", "application/json");
			put.addHeader("Content-type", "application/json");
			
			HttpResponse response = client.execute(put);
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

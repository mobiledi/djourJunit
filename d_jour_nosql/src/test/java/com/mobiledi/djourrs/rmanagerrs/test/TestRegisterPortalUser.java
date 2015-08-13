package com.mobiledi.djourrs.rmanagerrs.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.sound.midi.MidiDevice.Info;

import junit.framework.Assert;

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


public class TestRegisterPortalUser {
	private static Logger logger = LoggerFactory.getLogger(TestRegisterPortalUser.class);
	@Test
	public void test() {
		String link = "http://localhost:8080/d_jour_nosql/restservice/api/portal/registerrestaurant";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(link);
		StringEntity entity;
		try {
			entity = new StringEntity("{\"email\":\"ashish@gmail.com\",\"name\":\"managerRs\",\"banner_image\":\"109\",\"phone\":111111,\"website\":\"www.ash.com\",\"title\":\"ASHIH_RS\",\"password\":\"00000\",\"address_line1\":\"mataur\",\"address_line2\":\"daurala\",\"city\":\"meerut\",\"state\":\"uttar pradesh\",\"zip\":250221}");
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

package com.mobiledi.daos;

import org.codehaus.jackson.JsonNode;

import com.mobiledi.entities.RestaurantMaster;

public interface DjourManagerDAO {	
	public boolean persist(JsonNode djourInfo);
	public void update(RestaurantMaster master,JsonNode updateInfo);
	public void activate(RestaurantMaster master,JsonNode activateInfo);
	public void delete(RestaurantMaster master,JsonNode deleteInfo);
}

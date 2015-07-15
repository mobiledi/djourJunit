package com.mobiledi.daos;

import java.util.List;

import org.codehaus.jackson.JsonNode;

import com.mobiledi.entities.RestaurantMaster;

public interface RManagerDao {
	
	public List<RestaurantMaster> getAllRestaurants();
	public RestaurantMaster getRestaurant(int id);
	public boolean persistRestaurantBasicinfo(JsonNode toInsert);
	
}

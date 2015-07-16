package com.mobiledi.daos;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import com.mobiledi.entities.RestaurantMaster;

public interface RManagerDao {
	
	public JsonNode getAllRestaurants();
	public JsonNode getRestaurant(int id);
	
	
	public ObjectNode getRBasicinfo(int id);
	public ObjectNode getRAddressinfo(int id);
	public ObjectNode getRHoursinfo(int id);
	public ObjectNode getRTypesinfo(int id);
	
	
	
	public boolean persistRBasicinfo(JsonNode toInsert);
	public boolean persistRAddressinfo(RestaurantMaster master,JsonNode toInsert);
	public boolean persistRHoursinfo(RestaurantMaster master,JsonNode toInsert);
	public boolean persistRTypesinfo(RestaurantMaster master,JsonNode toInsert);
	
	
	public boolean updateRBasicinfo(RestaurantMaster master,JsonNode toInsert);
	public boolean updateRAddressinfo(RestaurantMaster master,JsonNode toInsert);
	public boolean updateRHoursinfo(RestaurantMaster master,JsonNode toInsert);
	public boolean updateRTypesinfo(RestaurantMaster master,JsonNode toInsert);
	
	
}

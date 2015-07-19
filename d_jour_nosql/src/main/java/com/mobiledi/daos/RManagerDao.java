package com.mobiledi.daos;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import com.mobiledi.entities.RestaurantMaster;

public interface RManagerDao {
	
	
	
	
	public JsonNode persistNewRestaurant(JsonNode toInsert);
	public JsonNode updateNewRestaurant(JsonNode toInsert);
	public JsonNode updateRestaurant(JsonNode toInsert);
	
	public JsonNode getAllRestaurants();
	public JsonNode getRestaurant(int id);
	public JsonNode getRestaurantProfile(JsonNode toget);
	
	public int authenticateUser(JsonNode toauthenticate);
	
/*	public ObjectNode getRBasicinfo(int id);
	public ObjectNode getRAddressinfo(int id);
	public ObjectNode getRHoursinfo(int id);
	public ObjectNode getRTypesinfo(int id);
	*/
	
	
	public boolean persistRBasicinfo(JsonNode toInsert);
	public boolean persistRAddressinfo(RestaurantMaster master,JsonNode toInsert);
	public boolean persistRHoursinfo(RestaurantMaster master,JsonNode toInsert);
	public boolean persistRTagsinfo(RestaurantMaster master,JsonNode toInsert);
	
	
	public boolean updateRBasicinfo(RestaurantMaster master,JsonNode toInsert);
	public boolean updateRAddressinfo(RestaurantMaster master,JsonNode toInsert);
	public boolean updateRHoursinfo(RestaurantMaster master,JsonNode toInsert);
	public boolean updateRTagsinfo(RestaurantMaster master,JsonNode toInsert);
	
	
}

package com.mobiledi.daos;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public interface RManagerDao {
	
	public JsonNode getAllRestaurants();
	public JsonNode getRestaurant(int id);
	
	
	public ObjectNode getRBasicinfo(int id);
	public ObjectNode getRAddressinfo(int id);
	public ObjectNode getRHoursinfo(int id);
	public ObjectNode getRTypesinfo(int id);
	
	
	
	public boolean persistRBasicinfo(JsonNode toInsert);
	public boolean persistRAddressinfo(JsonNode toInsert);
	public boolean persistRHoursinfo(JsonNode toInsert);
	public boolean persistRTypesinfo(JsonNode toInsert);
	
	
	public boolean updateRBasicinfo(JsonNode toInsert);
	public boolean updateRAddressinfo(JsonNode toInsert);
	public boolean updateRHoursinfo(JsonNode toInsert);
	public boolean updateRTypesinfo(JsonNode toInsert);
	
	
}

package com.mobiledi.daos;

import org.codehaus.jackson.JsonNode;

public interface DjourManagerDAO {	
	public void persist(JsonNode djourInfo);
	public void update(JsonNode updateInfo);
	public void activate(JsonNode activateInfo);
	public void delete(JsonNode deleteInfo);
}

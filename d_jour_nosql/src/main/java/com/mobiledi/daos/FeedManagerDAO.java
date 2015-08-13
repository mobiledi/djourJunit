package com.mobiledi.daos;

import org.codehaus.jackson.JsonNode;

public interface FeedManagerDAO {
	public void persist(JsonNode feedInfo);
	public void update(JsonNode updateInfo);
	public void delete(JsonNode deleteInfo);
}

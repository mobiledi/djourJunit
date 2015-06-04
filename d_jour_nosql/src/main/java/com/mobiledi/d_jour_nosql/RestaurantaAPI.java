package com.mobiledi.d_jour_nosql;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

@Path("/feed")
@Stateless
public class RestaurantaAPI {
	@EJB
	DjourPortalService portaldjour;
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/datahello/{name}")
	public String hello(@PathParam("name") String name) {
		return DjourService.sayHello(" "+name + " sent from datas");
	}
	
/*	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insert")
	public ObjectNode registerAppUser(JSON toInsert) {
		ObjectNode obj= new ObjectNode(JsonNodeFactory .instance);
		try {
			
			
			obj.put("status","ok");
			obj.put("Result", 1);
			
					
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("status","Failed");
			obj.put("Result", 0);
			return obj;
		}
	}*/
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/datahellonew")
	public ObjectNode hell3o() {
		ObjectNode obj= new ObjectNode(JsonNodeFactory .instance);
		//ArrayNode obj2= new ArrayNode(JsonNodeFactory .instance);
		
		try {
			
			
			obj.put("status","ok");
			obj.put("Result", 1);
			
			
			
		    JsonNode marksNode =new ArrayNode(JsonNodeFactory .instance);
	         
	         ((ArrayNode)marksNode).add(200);
	         ((ArrayNode)marksNode).add(90);
	         ((ArrayNode)marksNode).add(85);
	         
			obj.put("Marks", marksNode);
			//obj.putArray("marksNode").add(marksNode);
			
					
			
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("status","Failed");
			obj.put("Result", 0);
			
		}
		return obj;
}
	
}

package com.mobiledi.d_jour_nosql;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonNode;
@Path("/api")
@Stateless
public class DjourApi {
	@EJB
	DjourService djour;
	
	
	@GET
	//@Produces(MediaType.)
	@Produces({"application/javascript"})
	@Path("/sayhello/{name}")
	public String hello(@PathParam("name") String name) {
		return DjourService.sayHello(name + "new data");
	}
	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get")
	public List<DBObject> getDataFromDb() {
		return djour.getData();
		}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registerappuser")
	public String registerAppUser(JsonNode toInsert) {
		try {
			djour.persistAppUser(toInsert);		
			return "{\"status\":\"OK\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"status\":\"FAIL\"}";
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registerportaluser")
	public String registerPortalUser(JsonNode toInsert) {
		try {
			djour.persistPortalData(toInsert);					
			return "{\"status\":\"OK\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"status\":\"FAIL\"}";
		}
	}
	*/
}

package com.mobiledi.djourRS;

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

import com.mobiledi.djourDAO.DjourAppDAO;
@Path("/api")
@Stateless
public class DjourAppRS {
	@EJB
	DjourAppDAO djour;
	@GET
	@Produces({"application/javascript"})
	@Path("/sayhello/{name}")
	public String hello(@PathParam("name") String name) {
		return DjourAppDAO.sayHello(name + "new data");
	}

	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get")
	public List<DBObject> getDataFromDb() {
		return djour.getData();
		}
	*/
	/*App users registration*/
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
	
	/*App users authentication*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/appauthenticate/{username}/{password}")
	public String authenticateAppUser(@PathParam("username") String username,@PathParam("password") String password) {		
		boolean registered=djour.isUserRegisteredinApp(username,password);
		return "{\"authenticated\": \""+registered+ "\"}";	
		}
	
}

package com.mobiledi.djourRS;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jackson.JsonNode;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

import com.mobiledi.daos.RManagerDao;
import com.mobiledi.entities.RestaurantMaster;
import com.mobiledi.implementations.RManagerConcrete;
import com.mobiledi.utils.Constants;

@Path("/api/portal")
@Stateless
public class RManagerRS {
	@EJB
	RManagerDao implnt= new RManagerConcrete();
	
	
	/*get list of all restaurants listed*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getallrestaurants")
	public String getRestaurantLists() {
		List<RestaurantMaster> results=implnt.getAllRestaurants();
		return "{\"STATUS\":\"OK\"}";
		}
	
	
/*Portal users registration*/
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registeruserem")
	public Response registerPortalUser(JsonNode toInsert) {
		System.out.println("Register request from:" + toInsert.get(Constants.COLUMN_EMAIL));
		ResponseBuilder rs = new ResponseBuilderImpl();	
		try {
			if(implnt.persistRestaurantBasicinfo(toInsert))
			rs.status(Response.Status.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			rs.status(Response.Status.CONFLICT);
		}
		return rs.build();
	}
	
}

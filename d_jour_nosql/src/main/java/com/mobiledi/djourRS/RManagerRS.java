package com.mobiledi.djourRS;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jackson.JsonNode;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobiledi.daos.RManagerDao;
import com.mobiledi.entities.RestaurantMaster;
import com.mobiledi.implementations.RManagerConcrete;
import com.mobiledi.utils.Constants;

@Path("/api/portal")
@Stateless
public class RManagerRS {
	@EJB
	RManagerDao implnt= new RManagerConcrete();
	static Logger logger = LoggerFactory.getLogger(RManagerRS.class);
	
	/*get list of all restaurants listed*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getallrestaurants")
	public String getRestaurantLists() {
		//List<RestaurantMaster> results=implnt.getAllRestaurants();
		//return "{\"STATUS\":\"OK\"}";
		return implnt.getAllRestaurants().toString();
		}
	

	/*get list of all restaurants listed*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getrestaurant/{id}")
	public String getRestaurantWithId(@PathParam("id") int id) {
	/*	RestaurantMaster result=implnt.getRestaurant(id);
		return "{\"STATUS\":\"OK\"}";*/
		System.out.println("Value retrieving  to be sent...");
		logger.debug("Value retrieving  to be sent...");
		JsonNode toreturn = implnt.getRestaurant(id);
		System.out.println("Value to be sent"+ toreturn.toString());
		
		return toreturn.toString();
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
			if(implnt.persistRBasicinfo(toInsert))
			rs.status(Response.Status.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			rs.status(Response.Status.CONFLICT);
		}
		return rs.build();
	}
	
}

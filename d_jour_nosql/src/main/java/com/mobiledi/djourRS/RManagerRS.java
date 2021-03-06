package com.mobiledi.djourRS;



import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getrestaurantprofile")
	public String getRestaurantProfile(JsonNode toget) {
		JsonNode toreturn=implnt.getRestaurantProfile(toget);
		return toreturn.toString();
		}

	/*get list of all restaurants listed*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getrestaurant/{id}")
	public String getRestaurantWithId(@PathParam("id") int id) {
	/*	RestaurantMaster result=implnt.getRestaurant(id);
		return "{\"STATUS\":\"OK\"}";*/
		logger.debug("Value retrieving  to be sent...");
		JsonNode toreturn = implnt.getRestaurant(id);
		return toreturn.toString();
	}

/*Portal users registration*/
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registerrestaurant")
	public Response registerPortalUser(JsonNode toInsert) {
		logger.info("Register request from:" + toInsert.get(Constants.COLUMN_EMAIL));
		ResponseBuilder rs = new ResponseBuilderImpl();	
		try {
			if(implnt.persistRBasicinfo(toInsert))
			logger.info("=========  Atfer implements persistRBasicinfo  ======== ");
			rs.status(Response.Status.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			rs.status(Response.Status.CONFLICT);
		}
		return rs.build();
	}
	
/*Portal users update*/
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updatenewrestaurant")
	public Response updateNewPortalUser(JsonNode toInsert) {
		logger.info("Update request from:" + toInsert.get(Constants.COLUMN_EMAIL));
		ResponseBuilder rs = new ResponseBuilderImpl();	
		try {
			implnt.updateNewRestaurant(toInsert);
			logger.info("=========  Atfer implements updateNewResraurant======== ");
			rs.status(Response.Status.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			rs.status(Response.Status.CONFLICT);
		}
		return rs.build();
	}
		
	
	
/*Portal users update*/
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updaterestaurant")
	public Response updatePortalUser(JsonNode toInsert) {
		logger.info("Update request from:" + toInsert.get(Constants.COLUMN_EMAIL));
		ResponseBuilder rs = new ResponseBuilderImpl();	
		try {
			implnt.updateRestaurant(toInsert);
			rs.status(Response.Status.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			rs.status(Response.Status.CONFLICT);
		}
		return rs.build();
	}
	
	/*Portal users authentication*/
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/authenticateuser")
	public Response authenticatePortalUser(JsonNode toauthenticate) {	
		logger.info("Login Request String: " + toauthenticate.toString());
		logger.info("Login Request from: " + toauthenticate.get("username"));
		
		int registered=implnt.authenticateUser(toauthenticate);
		ResponseBuilder rs = new ResponseBuilderImpl();	
		
		if(registered!=0){
			
			rs.status(Response.Status.ACCEPTED);
			rs.entity("{\"master_id\":"+ registered+"}");
			
		}
		else
			rs.status(Response.Status.UNAUTHORIZED);
		
		logger.info("Login Request approved: " + registered);
			return rs.build();
		}
	

}

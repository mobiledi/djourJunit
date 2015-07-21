package com.mobiledi.djourRS;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jackson.JsonNode;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobiledi.daos.DjourManagerDAO;
import com.mobiledi.implementations.DjourManagerConcrete;
import com.mobiledi.utils.Constants;
@Path("/api/djour")
@Stateless
public class RestaurantDjourRS {
	@EJB
	DjourManagerDAO implntDjour= new DjourManagerConcrete();
	static Logger logger = LoggerFactory.getLogger(RestaurantDjourRS.class);
/*Portal users registration*/
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/adddjour")
	public Response registerPortalUser(JsonNode toInsert) {
		logger.info("Register request from:" + toInsert.get(Constants.COLUMN_EMAIL));
		ResponseBuilder rs = new ResponseBuilderImpl();	
		try {
			if(implntDjour.persist(toInsert))
			rs.status(Response.Status.CREATED);
		} catch (Exception e) {
		//	e.printStackTrace();
			rs.status(Response.Status.CONFLICT);
		}
		return rs.build();
	}
	

}

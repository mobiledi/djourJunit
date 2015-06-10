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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

@Path("/api/portal")
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
	
	
	
	/*Portal users registration*/
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registeruser")
	public Response registerPortalUser(JsonNode toInsert) {
		System.out.println("Register request from:" + toInsert.get(Constants.COLUMN_EMAIL));
		ResponseBuilder rs = new ResponseBuilderImpl();	
		try {
			if(portaldjour.persistSignUpData(toInsert))
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
	@Path("/authenticate")
	public String authenticatePortalUser(JsonNode toauthenticate) {		
		boolean registered=portaldjour.isUserRegisteredinportal(toauthenticate);
		
		ObjectNode obj= new ObjectNode(JsonNodeFactory.instance);
		obj.put("authenticated", registered);	
		return obj.toString();
		  //return '{"authenticated"}'
		 // return "{\"authenticated\": \""+"true"+ "\"}";	
		}
	
	
	/*Portal users registration*/
	
	/*@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registeruser")
	public String registerPortalUser(JsonNode toInsert) {
		System.out.println("Register request from:" + toInsert.get(Constants.COLUMN_EMAIL));
		
		try {
			portaldjour.persistSignUpData(toInsert);					
			return "{\"status\":\"OK\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"status\":\"FAIL\"}";
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
/*Portal update registration*/
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateuser")
	public String updatePortalUser(JsonNode toInsert) {
		try {
			portaldjour.updateSignUpData(toInsert);
			return "{\"status\":\"OK\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"status\":\"FAIL\"}";
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getlatlong")
	public String getLatLong(JsonNode toInsert) {
		try {
			portaldjour.getCo_ordinates(toInsert.get("address").getTextValue());					
			return "{\"status\":\"OK\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"status\":\"FAIL\"}";
		}
	}
	
}

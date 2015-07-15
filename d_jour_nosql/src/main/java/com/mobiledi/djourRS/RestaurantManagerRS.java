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
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

import com.google.code.geocoder.model.LatLng;
import com.mobiledi.djourDAO.DjourAppDAO;
import com.mobiledi.djourDAO.RestaurantManagerDAO;
import com.mobiledi.utils.Constants;

@Path("/api/portal")
@Stateless
public class RestaurantManagerRS {
	@EJB
	RestaurantManagerDAO portaldjour;
	
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/datahello/{name}")
	public String hello(@PathParam("name") String name) {
		return DjourAppDAO.sayHello(" "+name + " sent from datas");
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getprofile")
	public JsonNode getProfile(JsonNode toget) {
		JsonNode toreturn=portaldjour.getUserDetails(toget);
		//System.out.println("Get Profile returns:" + toreturn);
		//String user=toget.get("username").asText();
		return toreturn;
		}
	
	/*get list of all restaurants listed*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getrestaurants")
	public String getRestaurantLists() {
		JsonNode toreturn=portaldjour.getRestaurants();
		return toreturn.toString();
		}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getrestaurants/{id}")
	public String getRestaurantprofile(@PathParam("id") int id) {
		JsonNode toreturn=portaldjour.getRestaurantProfile(id);
		return toreturn.toString();
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
	public Response authenticatePortalUser(JsonNode toauthenticate) {	
		System.out.println("Login Request String: " + toauthenticate.toString());
		System.out.println("Login Request from: " + toauthenticate.get("username"));
		
		boolean registered=portaldjour.isUserRegisteredinportal(toauthenticate);
		
		ResponseBuilder rs = new ResponseBuilderImpl();	
		
		if(registered)
			rs.status(Response.Status.ACCEPTED);
		
		else
			rs.status(Response.Status.UNAUTHORIZED);
		return rs.build();
		//ObjectNode obj= new ObjectNode(JsonNodeFactory.instance);
		//obj.put("authenticated", registered);	
		
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
			//System.out.println("Request string:" + toInsert.toString());
			portaldjour.updateSignUpData(toInsert);
			return "{\"status\":\"OK\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"status\":\"FAIL\"}";
		}
	}
	
	
/*Portal New update registration*/
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updatenewuser")
	public String updateNewPortalUser(JsonNode toInsert) {
		try {
			//System.out.println("Request string:" + toInsert.toString());
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
		ObjectNode toreturn=new ObjectNode(JsonNodeFactory.instance);
		try {
			LatLng latLong= portaldjour.getCo_ordinates(toInsert.get("address").getTextValue());					
			
			toreturn.put("Status", "ok");
			toreturn.put("address", toInsert.get("address").getTextValue());
			toreturn.put("latitude", latLong.getLat());
			toreturn.put("longitude", latLong.getLng());		
			
			
		} catch (Exception e) {
			e.printStackTrace();
			toreturn.put("Status", "fail");
		}
		return toreturn.toString();
	}
	
	//DB test query
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/gettypes")
	public JsonNode getTypes() {
		JsonNode obj= portaldjour.getAllTypes();
		return obj;
	}
	
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/gettest")
	public String gettestem() {
		portaldjour.testEm();
		return "{\"STATUS\":\"OK\"}";
	}
	
	
}

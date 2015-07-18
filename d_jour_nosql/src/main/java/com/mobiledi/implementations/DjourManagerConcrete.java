package com.mobiledi.implementations;

import java.sql.Timestamp;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.codehaus.jackson.JsonNode;

import com.mobiledi.daos.DjourManagerDAO;
import com.mobiledi.entities.RestaurantDjour;
import com.mobiledi.entities.RestaurantMaster;
import com.mobiledi.utils.Constants;

@Stateless
public class DjourManagerConcrete implements DjourManagerDAO {
	@PersistenceContext(unitName = "d_jour_nosql")
	EntityManager entityManager;
	
	
	@Override
	public void persist(RestaurantMaster master,JsonNode djourInfo) {
		  
		  entityManager.persist(jsonToObj(master, djourInfo));
		
	}

	@Override
	public void update(RestaurantMaster master,JsonNode updateInfo) {
		entityManager.merge(jsonToObj(master, updateInfo));
	}

	@Override
	public void activate(RestaurantMaster master,JsonNode activateInfo) {
		

	}

	@Override
	public void delete(RestaurantMaster master,JsonNode deleteInfo) {
		entityManager.remove(jsonToObj(master, deleteInfo));
			
	}
	
	
	private RestaurantDjour jsonToObj(RestaurantMaster master, JsonNode djourInfo){
		
		 RestaurantMaster restaurant_master=master;
		  int fk_restauran_id = djourInfo.get(restaurant_master.getId()).getIntValue();
		  boolean active_status = djourInfo.get(Constants.DJOUR_STATUS).getBooleanValue();  
		  String special_name= djourInfo.get(Constants.DJOUR_STATUS).asText();   
		  String description = djourInfo.get(Constants.DJOUR_STATUS).asText();   		  
		  int special_type = djourInfo.get(Constants.DJOUR_STATUS).getIntValue();   
		  float price= (float) djourInfo.get(Constants.DJOUR_STATUS).getDoubleValue();   
		  boolean repeat= djourInfo.get(Constants.DJOUR_STATUS).getBooleanValue();   
		  String start_from= djourInfo.get(Constants.DJOUR_STATUS).getTextValue();   
		  String end_on = djourInfo.get(Constants.DJOUR_STATUS).getTextValue();   
		  String create_date = djourInfo.get(Constants.DJOUR_STATUS).getTextValue();   
		  
		  RestaurantDjour djourObj= new RestaurantDjour();
		  djourObj.setRestaurantMaster(master);
		  djourObj.setActiveStatus(active_status);
		  djourObj.setSpecialName(special_name);
		  djourObj.setDescription(description);
		  djourObj.setSpecialType(special_type);
		  djourObj.setPrice(price);
		  djourObj.setRepeat(repeat);
		  djourObj.setStartFrom( Timestamp.valueOf(start_from));
		  djourObj.setEndOn( Timestamp.valueOf(end_on));
		  djourObj.setCreateDate(new Timestamp(new Date().getTime()));
		  
		return djourObj;
	}

}

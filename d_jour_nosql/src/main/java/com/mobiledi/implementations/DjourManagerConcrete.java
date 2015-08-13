package com.mobiledi.implementations;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobiledi.daos.DjourManagerDAO;
import com.mobiledi.djourRS.RestaurantDjourRS;
import com.mobiledi.entities.RestaurantAddress;
import com.mobiledi.entities.RestaurantDjour;
import com.mobiledi.entities.RestaurantMaster;
import com.mobiledi.utils.Constants;

@Stateless
public class DjourManagerConcrete implements DjourManagerDAO {
	static Logger logger = LoggerFactory.getLogger(DjourManagerConcrete.class);
	@PersistenceContext(unitName = "d_jour_nosql")
	EntityManager entityManager;
	
	
	@Override
	public boolean persist(JsonNode djourInfo) {
		logger.info("Request to insert new djour for: " + djourInfo.get(Constants.DJOUR_JSON_MASTER_ID).asInt() + " with Item "+ djourInfo.get(Constants.DJOUR_SPECIAL_NAME).asInt());
		try{
		RestaurantMaster master=getMaster(djourInfo.get(Constants.DJOUR_JSON_MASTER_ID).asInt());
		  
		  entityManager.persist(jsonToObj(master, djourInfo));
		return true;}
		catch(Exception e){
			logger.info("Djour insert failed!");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void update(RestaurantMaster master,JsonNode updateInfo) {
		Query query = entityManager
				.createNamedQuery("RestaurantDjour.findAllWithId");
		query.setParameter("id", master.getId());
		List<RestaurantAddress> listToUpdate=query.getResultList();
		for (int i = 0; i < listToUpdate.size(); i++) {
			
			
			entityManager.merge(listToUpdate.get(i));
		}
		
		
		
		//entityManager.merge(jsonToObj(master, updateInfo));
	}

	@Override
	public void activate(RestaurantMaster master,JsonNode activateInfo) {
		

	}

	@Override
	public void delete(RestaurantMaster master,JsonNode deleteInfo) {
		entityManager.remove(jsonToObj(master, deleteInfo));
			
	}
	
	
	private RestaurantDjour jsonToObj(RestaurantMaster master, JsonNode djourInfo){
		
		// RestaurantMaster restaurant_master=master;
		//  int fk_restauran_id = djourInfo.get(restaurant_master.getId()).getIntValue();
		  boolean active_status = djourInfo.get(Constants.DJOUR_STATUS).getBooleanValue();  
		  String special_name= djourInfo.get(Constants.DJOUR_SPECIAL_NAME).asText();   
		  String description = djourInfo.get(Constants.DJOUR_DESCRIPTION).asText();   		  
		  int special_type = djourInfo.get(Constants.DJOUR_SPECIAL_TYPE).getIntValue();   
		  float price= (float) djourInfo.get(Constants.DJOUR_PRICE).asDouble();   
		  boolean repeat= djourInfo.get(Constants.DJOUR_REPEATE).getBooleanValue();   
		  String start_from= djourInfo.get(Constants.DJOUR_START_FROM).getTextValue(); 
		  String end_on = djourInfo.get(Constants.DJOUR_END_ON).getTextValue();   		   
		  String start_time= djourInfo.get(Constants.DJOUR_START_TIME).getTextValue(); 
		  String end_time = djourInfo.get(Constants.DJOUR_END_TIME).getTextValue();  
		    
		  RestaurantDjour djourObj= new RestaurantDjour();
		  djourObj.setRestaurantMaster(master);
		  djourObj.setActiveStatus(active_status);
		  djourObj.setSpecialName(special_name);
		  djourObj.setDescription(description);
		  djourObj.setSpecialType(special_type);
		  djourObj.setPrice(price);
		  djourObj.setRepeat(repeat);
		  //TODO change static date to get from json 
		  
		  String start_date_time=start_from.concat(start_time.substring(15,23));
		  String end_date_time=end_on.concat(end_time.substring(15,23));
		  
		  logger.info("DATE OBJs" + start_date_time + " "+ end_date_time);
		  
		  djourObj.setStartFrom( Timestamp.valueOf(start_date_time));
		  djourObj.setEndOn( Timestamp.valueOf(end_date_time));
		  djourObj.setCreateDate(new Timestamp(new Date().getTime()));
		  
		return djourObj;
	}
	private RestaurantMaster getMaster(int id){
		Query query = entityManager
				.createNamedQuery("RestaurantMaster.findOne");
		query.setParameter("id", id);
		//query.setParameter("restaurantkey", restaurantkey);
		List<RestaurantMaster> list=query.getResultList();
		for (int i = 0; i < list.size(); i++) {
			return list.get(i);
		}	
		return null;
	}

}

package com.mobiledi.implementations;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;
import com.mobiledi.daos.RManagerDao;
import com.mobiledi.entities.RestaurantAddress;
import com.mobiledi.entities.RestaurantMaster;
import com.mobiledi.utils.Constants;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
@Stateless
public class RManagerConcrete implements RManagerDao{
	static Logger logger = LoggerFactory.getLogger(RManagerConcrete.class);
	 @PersistenceContext(unitName="d_jour_nosql")
	 private EntityManager entityManager;
	
	
	@Override
	public List<RestaurantMaster> getAllRestaurants() {
		List<RestaurantMaster> results=new ArrayList<RestaurantMaster>();
			results = entityManager.createNamedQuery("RestaurantMaster.findAll").getResultList();
		
		for(int i=0;i<results.size();i++){
		
			logger.info("Tag master name = " + results.get(i).getName());
			//logger.info("Tag hour = " +results.get(i).getRestaurantHours().get(0).getWeekdayOpeningHour());
			logger.info("Tag address = " + results.get(i).getRestaurantAddresses().get(0).getAddressLine1());
			Geometry geom= results.get(i).getRestaurantAddresses().get(0).getGeom();
			
			logger.info("Geometry Co-ordinates: "+ geom.getCoordinates()[0]);
			logger.info("Geocode Longitude="+ results.get(i).getRestaurantAddresses().get(0).getLongitude());
			logger.info("Geocode Latitude="+ results.get(i).getRestaurantAddresses().get(0).getLatitude());
	
		}
		return results;

	}

	@Override
	public RestaurantMaster getRestaurant(int id) {
		Query query=entityManager.createNamedQuery("RestaurantMaster.findOne");
		query.setParameter("id", id);
		List<RestaurantMaster> results= query.getResultList();
		for(int i=0;i<results.size();i++){
			
			logger.info("Single master name = " + results.get(i).getName());
			//logger.info("Tag hour = " +results.get(i).getRestaurantHours().get(0).getWeekdayOpeningHour());
			logger.info("Tag address = " + results.get(i).getRestaurantAddresses().get(0).getAddressLine1());
			Geometry geom= results.get(i).getRestaurantAddresses().get(0).getGeom();
			
			logger.info("Geometry Co-ordinates: "+ geom.getCoordinates()[0]);
			logger.info("Geocode Longitude="+ results.get(i).getRestaurantAddresses().get(0).getLongitude());
			logger.info("Geocode Latitude="+ results.get(i).getRestaurantAddresses().get(0).getLatitude());
			return results.get(i);
		}
		
return null;
	}

	@Override
	public boolean persistRestaurantBasicinfo(JsonNode toInsert) {
		
		logger.debug("Insert Data:" +toInsert.toString());

		String name = toInsert.get(Constants.COLUMN_NAME).asText();
		String address_line1 = toInsert.get(Constants.COLUMN_ADD1).asText();
		String address_line2 = toInsert.get(Constants.COLUMN_ADD2).asText();
		String city = toInsert.get(Constants.COLUMN_CITY).asText();
		String state = toInsert.get(Constants.COLUMN_STATE).asText();
		int zip = toInsert.get(Constants.COLUMN_ZIP).asInt();
		LatLng geos = getCo_ordinates(name + ", " + address_line1 + ", "
				+ address_line2 + ", " + city + ", " + state + ", " + zip);

		
		try {
			ObjectMapper mapper = new ObjectMapper();
			RestaurantMaster restObj=mapper.readValue(toInsert.toString(), RestaurantMaster.class);
			
			mapper= new ObjectMapper();
			RestaurantAddress restAdd=mapper.readValue(toInsert.toString(), RestaurantAddress.class);
			
			logger.info("Address info"+restAdd.getAddressLine1());
			GeometryFactory geometryFactory = new GeometryFactory(); 
			
		    Coordinate coord = new Coordinate(geos.getLng().doubleValue(), geos.getLat().doubleValue());
		    Point point = geometryFactory.createPoint(coord);
		    point.setSRID(4326);
		    geometryFactory.createGeometry(point);
			
		    restAdd.setRestaurantMaster(restObj);
		    restAdd.setAddressLine1(address_line1);
		    restAdd.setAddressLine2(address_line2);
		    
		    restAdd.setLatitude(geos.getLat().doubleValue());
		    restAdd.setLongitude(geos.getLng().doubleValue());
			restAdd.setGeom(point);
			restAdd.setCreateDate(new Timestamp(new Date().getTime()));
			restAdd.setActiveFlag(1);
			List<RestaurantAddress> addresses=new ArrayList<RestaurantAddress> ();
			addresses.add(restAdd);
			restObj.setRestaurantAddresses(addresses);
			entityManager.persist(restObj);
			entityManager.persist(restAdd);
			
		logger.info("email info"+restObj.getEmail());
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public LatLng getCo_ordinates(String address) {
		final Geocoder geocoder = new Geocoder();
		try {
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
					.setAddress(address).setLanguage("en").getGeocoderRequest();
			GeocodeResponse geocoderResponse = geocoder
					.geocode(geocoderRequest);
			List<GeocoderResult> results = geocoderResponse.getResults();
			for (GeocoderResult result : results) {
				LatLng latLong = result.getGeometry().getLocation();
				logger.debug("Geocoder returned for address: "+address+" Lat: " + latLong.getLat() + " Long"
						+ latLong.getLng());
				
				return latLong;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}

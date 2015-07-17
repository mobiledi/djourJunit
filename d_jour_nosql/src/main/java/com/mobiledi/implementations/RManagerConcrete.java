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
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
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
import com.mobiledi.entities.RestaurantHour;
import com.mobiledi.entities.RestaurantMaster;
import com.mobiledi.entities.RestaurantTag;
import com.mobiledi.utils.Constants;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

@Stateless
public class RManagerConcrete implements RManagerDao {
	final private static int INACTIVE_FLAG=0;
	final private static int ACTIVE_FLAG=1;
	
	private static Logger logger = LoggerFactory.getLogger(RManagerConcrete.class);
	@PersistenceContext(unitName = "d_jour_nosql")
	private EntityManager entityManager;

	@Override
	public JsonNode getAllRestaurants() {
		
		try{
		List<RestaurantMaster> results = new ArrayList<RestaurantMaster>();
		results = entityManager.createNamedQuery("RestaurantMaster.findAll")
				.getResultList();
		ArrayNode toreturn = new ArrayNode(JsonNodeFactory.instance);
		for (int i = 0; i < results.size(); i++) {
			ObjectNode resultNode = new ObjectNode(JsonNodeFactory.instance);
			/*
			 * logger.info("Tag master name = " + results.get(i).getName()); //
			 * logger.info("Tag hour = " //
			 * +results.get(i).getRestaurantHours().
			 * get(0).getWeekdayOpeningHour()); logger.info("Tag address = " +
			 * results.get(i).getRestaurantAddresses().get(0)
			 * .getAddressLine1()); Geometry geom =
			 * results.get(i).getRestaurantAddresses().get(0) .getGeom();
			 * 
			 * logger.info("Geometry Co-ordinates: " +
			 * geom.getCoordinates()[0]); logger.info("Geocode Longitude=" +
			 * results.get(i).getRestaurantAddresses().get(0) .getLongitude());
			 * logger.info("Geocode Latitude=" +
			 * results.get(i).getRestaurantAddresses().get(0) .getLatitude());
			 */
			resultNode = (ObjectNode) convertToJsonNode(results.get(i));

			toreturn.add(resultNode);
		}
		return toreturn;
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		ObjectNode failedObj = new ObjectNode(JsonNodeFactory.instance);
		failedObj.put("Status", "No records in DB");
		return failedObj;
	}

	@Override
	public JsonNode getRestaurant(int id) {
		Query query = entityManager
				.createNamedQuery("RestaurantMaster.findOne");
		query.setParameter("id", id);

		try {
			@SuppressWarnings("unchecked")
			List<RestaurantMaster> results = query.getResultList();

			return convertToJsonNode(results.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * for(int i=0;i<results.size();i++){
		 * 
		 * logger.info("Single master name = " + results.get(i).getName());
		 * //logger.info("Tag hour = "
		 * +results.get(i).getRestaurantHours().get(0).getWeekdayOpeningHour());
		 * logger.info("Tag address = " +
		 * results.get(i).getRestaurantAddresses().get(0).getAddressLine1());
		 * Geometry geom=
		 * results.get(i).getRestaurantAddresses().get(0).getGeom();
		 * 
		 * logger.info("Geometry Co-ordinates: "+ geom.getCoordinates()[0]);
		 * logger.info("Geocode Longitude="+
		 * results.get(i).getRestaurantAddresses().get(0).getLongitude());
		 * logger.info("Geocode Latitude="+
		 * results.get(i).getRestaurantAddresses().get(0).getLatitude());
		 * 
		 * }
		 */
		ObjectNode failedObj = new ObjectNode(JsonNodeFactory.instance);
		failedObj.put("Status", "No Such record");
		return failedObj;
	}

	@Override
	public boolean persistRBasicinfo(JsonNode toInsert) {
		logger.debug("Insert Data:" + toInsert.toString());
		RestaurantMaster masterObj=new RestaurantMaster();
		try {
			String name = toInsert.get(Constants.COLUMN_NAME).asText();
			
			String email = toInsert.get(Constants.COLUMN_EMAIL).asText();	
			byte[] banner_image=toInsert.get(Constants.COLUMN_BANNER_IMAGE).getBinaryValue();
			String phone = toInsert.get(Constants.COLUMN_PHONE).asText();
			String website = toInsert.get(Constants.COLUMN_WEBSITE).asText();
			masterObj.setName(name);
			//New Registrations don't have titles until update
			if(toInsert.has(Constants.COLUMN_TITLE))
			{
			String title = toInsert.get(Constants.COLUMN_TITLE).asText();
			masterObj.setTitle(title);
			}
			masterObj.setEmail(email);
			masterObj.setBannerImage(banner_image);
			//updates  don't have password field 
			if(toInsert.has(Constants.COLUMN_PASSWORD)){
				String password = toInsert.get(Constants.COLUMN_PASSWORD).asText();
				masterObj.setPassword(password);
				}
			masterObj.setPhone(phone);
			masterObj.setWebsite(website);
			entityManager.persist(masterObj);
			persistRAddressinfo(masterObj, toInsert);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean persistRAddressinfo (RestaurantMaster master,JsonNode toInsert) {

		
		try {
			
			RestaurantAddress restAdd =new	RestaurantAddress();
			restAdd.setRestaurantMaster(master);
			
			
			String name = toInsert.get(Constants.COLUMN_NAME).asText();
			String address_line1 = toInsert.get(Constants.COLUMN_ADD1).asText();
			String address_line2 = toInsert.get(Constants.COLUMN_ADD2).asText();
			String city = toInsert.get(Constants.COLUMN_CITY).asText();
			String state = toInsert.get(Constants.COLUMN_STATE).asText();
			int zip = toInsert.get(Constants.COLUMN_ZIP).asInt();
			LatLng geos = getCo_ordinates(name + ", " + address_line1 + ", "
					+ address_line2 + ", " + city + ", " + state + ", " + zip);


			GeometryFactory geometryFactory = new GeometryFactory();

			Coordinate coord = new Coordinate(geos.getLng().doubleValue(), geos
					.getLat().doubleValue());
			Point point = geometryFactory.createPoint(coord);
			point.setSRID(4326);
			geometryFactory.createGeometry(point);
			
			restAdd.setAddressLine1(address_line1);
			restAdd.setAddressLine2(address_line2);
			restAdd.setCity(city);
			restAdd.setState(state);
			restAdd.setZip(zip);
			restAdd.setLatitude(geos.getLat().doubleValue());
			restAdd.setLongitude(geos.getLng().doubleValue());
			restAdd.setGeom(point);
			restAdd.setCreateDate(new Timestamp(new Date().getTime()));
			restAdd.setActiveFlag(ACTIVE_FLAG);
			entityManager.persist(restAdd);
			return true;
			}
		catch(Exception e){		
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean persistRHoursinfo(RestaurantMaster master,JsonNode toInsert) {
		
		
		int wd_o_h = toInsert.get(Constants.COLUMN_WEEKDAY_OPENING_HOUR)
				.asInt();
		int wd_o_m = toInsert.get(Constants.COLUMN_WEEKDAY_OPENING_MINUTES)
				.asInt();
		int we_o_h = toInsert.get(Constants.COLUMN_WEEKEND_OPENING_HOUR)
				.asInt();
		int we_o_m = toInsert.get(Constants.COLUMN_WEEKEND_OPENING_MINUTES)
				.asInt();

		int wd_c_h = toInsert.get(Constants.COLUMN_WEEKDAY_CLOSING_HOUR)
				.asInt();
		int wd_c_m = toInsert.get(Constants.COLUMN_WEEKDAY_CLOSING_MINUTES)
				.asInt();
		int we_c_h = toInsert.get(Constants.COLUMN_WEEKEND_CLOSING_HOUR)
				.asInt();
		int we_c_m = toInsert.get(Constants.COLUMN_WEEKEND_CLOSING_MINUTES)
				.asInt();
		
		RestaurantHour hourObj= new RestaurantHour();
		hourObj.setRestaurantMaster(master);
		hourObj.setActiveFlag(ACTIVE_FLAG);
		hourObj.setCreateDatetime(new Timestamp(new Date().getTime()));
		
		hourObj.setWeekdayOpeningHour(wd_o_h);
		hourObj.setWeekdayOpeningMinutes(wd_o_m);
		
		hourObj.setWeekdayClosingHour(wd_c_h);
		hourObj.setWeekdayClosingMinutes(wd_c_m);
		
		hourObj.setWeekendOpeningHour(we_o_h);
		hourObj.setWeekendOpeningMinutes(we_o_m);
		
		hourObj.setWeekendClosingHour(we_c_h);
		hourObj.setWeekendClosingMinutes(we_c_m);
		
		entityManager.persist(hourObj);
		
		return false;
	}

	@Override
	public boolean persistRTagsinfo(RestaurantMaster master,JsonNode toInsert) {
		boolean tagsArray[] = new boolean[4];
		tagsArray[0] = toInsert.get(Constants.ORGANIC).asBoolean();
		tagsArray[1] = toInsert.get(Constants.GLUTEN_FREE).asBoolean();
		tagsArray[2] = toInsert.get(Constants.VEG).asBoolean();
		tagsArray[3] = toInsert.get(Constants.VEGAN).asBoolean();
		
		for(int i=0;i<4;i++){
			if(tagsArray[i]){
				RestaurantTag tagsObj= new RestaurantTag();
				tagsObj.setRestaurantMaster(master);
				tagsObj.setFkProfileTagsId(i+1);
				entityManager.persist(tagsObj);
			}
						
		}	
		return true;
	}

	@Override
	public boolean updateRBasicinfo(RestaurantMaster master,JsonNode toInsert) {
		try {
			String name = toInsert.get(Constants.COLUMN_NAME).asText();
			String title = toInsert.get(Constants.COLUMN_TITLE).asText();
			String email = toInsert.get(Constants.COLUMN_EMAIL).asText();	
			byte[] banner_image=toInsert.get(Constants.COLUMN_BANNER_IMAGE).getBinaryValue();
			String phone = toInsert.get(Constants.COLUMN_PHONE).asText();
			String website = toInsert.get(Constants.COLUMN_WEBSITE).asText();		
			master.setName(name);
			master.setTitle(title);
			master.setEmail(email);
			master.setBannerImage(banner_image);
			master.setPhone(phone);
			master.setWebsite(website);
			entityManager.merge(master);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}

	@Override
	public boolean updateRAddressinfo(RestaurantMaster master,JsonNode toInsert) {
	
		Query query = entityManager
				.createNamedQuery("RestaurantAddress.findAllWithId");
		query.setParameter("id", master.getId());
		List<RestaurantAddress> listToUpdate=query.getResultList();
		for (int i = 0; i < listToUpdate.size(); i++) {
			listToUpdate.get(i).setActiveFlag(INACTIVE_FLAG);
			entityManager.merge(listToUpdate.get(i));
		}
		
		persistRAddressinfo(master, toInsert);
		return true;
	}

	@Override
	public boolean updateRHoursinfo(RestaurantMaster master,JsonNode toInsert) {

		Query query = entityManager
				.createNamedQuery("RestaurantHour.findAllWithId");
		query.setParameter("id", master.getId());
		List<RestaurantHour> listToUpdate=query.getResultList();
		for (int i = 0; i < listToUpdate.size(); i++) {
			listToUpdate.get(i).setActiveFlag(INACTIVE_FLAG);
			entityManager.merge(listToUpdate.get(i));
		}
		
		persistRHoursinfo(master, toInsert);
		return true;
		
	}

	@Override
	public boolean updateRTagsinfo(RestaurantMaster master,JsonNode toInsert) {
		Query query = entityManager
				.createNamedQuery("RestaurantTag.findAllWithId");
		query.setParameter("id", master.getId());
		List<RestaurantTag> listToUpdate=query.getResultList();
		for (int i = 0; i < listToUpdate.size(); i++) {
			entityManager.remove(listToUpdate.get(i));
			
		}
		persistRTagsinfo(master, toInsert);
		return true;
	}

	private JsonNode convertToJsonNode(RestaurantMaster toConvert) {
		ObjectNode toreturn = new ObjectNode(JsonNodeFactory.instance);

		try {
			int id = toConvert.getId();
			String name = toConvert.getName();
			String title = toConvert.getTitle();
			String email = toConvert.getEmail();
			byte[] image = toConvert.getBannerImage();
			String phone = toConvert.getPhone();
			String website = toConvert.getWebsite();

			toreturn.put("id", id);
			toreturn.put("name", name);
			toreturn.put("title", title);
			toreturn.put("email", email);
			toreturn.put("banner_image", image);
			toreturn.put("phone", phone);
			toreturn.put("website", website);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			RestaurantAddress addObj=null;
			for(RestaurantAddress ctrAddObj:toConvert.getRestaurantAddresses()){
				if(ctrAddObj.getActiveFlag()==ACTIVE_FLAG)
					addObj=ctrAddObj;
				
			}
			logger.debug("Active Address row create date:" + addObj.getCreateDate());
			System.out.println("Active Address row create date:" + addObj.getCreateDate());
			String address_line1 = addObj.getAddressLine1();
			String address_line2 = addObj.getAddressLine2();
			String city = addObj.getCity();
			String state = addObj.getState();
			int zip = addObj.getZip();
			double lat = addObj.getLatitude();
			double lng = addObj.getLongitude();

			toreturn.put(Constants.COLUMN_ADD1, address_line1);
			toreturn.put(Constants.COLUMN_ADD2, address_line2);
			toreturn.put(Constants.COLUMN_CITY, city);
			toreturn.put(Constants.COLUMN_STATE, state);
			toreturn.put(Constants.COLUMN_ZIP, zip);
			toreturn.put("lat", lat);
			toreturn.put("long", lng);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			RestaurantHour hourObj=null;
			for(RestaurantHour ctrhourObj:toConvert.getRestaurantHours()){
				if(ctrhourObj.getActiveFlag()==ACTIVE_FLAG)
					hourObj=ctrhourObj;
				
			}
			logger.debug("Debug Active Hour row create date:" + hourObj.getCreateDatetime());
			System.out.println("Active Hour row create date:" + hourObj.getCreateDatetime());
			int WDOH = hourObj.getWeekdayOpeningHour();
			int WEOH = hourObj.getWeekendOpeningHour();
			int WDOM = hourObj.getWeekdayOpeningMinutes();
			int WEOM = hourObj.getWeekendOpeningMinutes();
			int WDCH = hourObj.getWeekdayClosingHour();
			int WECH = hourObj.getWeekendClosingHour();
			int WDCM = hourObj.getWeekdayClosingMinutes();
			int WECM = hourObj.getWeekendClosingMinutes();

			toreturn.put("weekday_opening_hour", WDOH);
			toreturn.put("weekend_opening_hour", WEOH);

			toreturn.put("weekday_opening_minutes", WDOM);
			toreturn.put("weekend_opening_minutes", WEOM);

			toreturn.put("weekday_closing_hour", WDCH);
			toreturn.put("weekend_closing_hour", WECH);

			toreturn.put("weekday_closing_minutes", WDCM);
			toreturn.put("weekend_closing_minutes", WECM);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			List<RestaurantTag> tagObj = new ArrayList<RestaurantTag>();
			tagObj = toConvert.getRestaurantTags();
			String type_name[] = { Constants.ORGANIC, Constants.GLUTEN_FREE,
					Constants.VEG, Constants.VEGAN };

			boolean types[] = new boolean[4];
			types[0] = false;
			types[1] = false;
			types[2] = false;
			types[3] = false;

			for (int i = 0; i < tagObj.size(); i++) {
				switch (tagObj.get(i).getFkProfileTagsId()) {

				case (1):
					types[0] = true;
					break;
				case (2):
					types[1] = true;
					break;
				case (3):
					types[2] = true;
					break;
				case (4):
					types[3] = true;
					break;
				}

			}

			for (int i = 0; i < 4; i++) {
				if (types[i]) {
					toreturn.put(Constants.ORGANIC, types[0]);
					toreturn.put(Constants.GLUTEN_FREE, types[1]);
					toreturn.put(Constants.VEG, types[2]);
					toreturn.put(Constants.VEGAN, types[3]);
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toreturn;

	}

	private LatLng getCo_ordinates(String address) {
		final Geocoder geocoder = new Geocoder();
		try {
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
					.setAddress(address).setLanguage("en").getGeocoderRequest();
			GeocodeResponse geocoderResponse = geocoder
					.geocode(geocoderRequest);
			List<GeocoderResult> results = geocoderResponse.getResults();
			for (GeocoderResult result : results) {
				LatLng latLong = result.getGeometry().getLocation();
				logger.debug("Geocoder returned for address: " + address
						+ " Lat: " + latLong.getLat() + " Long"
						+ latLong.getLng());

				return latLong;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public JsonNode persistNewRestaurant(JsonNode toInsert) {	
		persistRBasicinfo(toInsert);
		return null;
	}
	
	@Override
	public JsonNode updateNewRestaurant(JsonNode toInsert) {
		RestaurantMaster master=getIdfromEmail(toInsert.get(Constants.USERNAME).asText());
		updateRBasicinfo(master, toInsert);
		updateRAddressinfo(master, toInsert);
		persistRHoursinfo(master, toInsert);
		persistRTagsinfo(master, toInsert);	
		return null;
	}

	@Override
	public JsonNode updateRestaurant(JsonNode toInsert) {
		RestaurantMaster master=getIdfromEmail(toInsert.get(Constants.USERNAME).asText());
		//TODO fetch master
		updateRBasicinfo(master, toInsert);
		updateRAddressinfo(master, toInsert);
		updateRHoursinfo(master, toInsert);
		updateRTagsinfo(master, toInsert);
		
		return null;
	}
	
	
	private RestaurantMaster getIdfromEmail(String email){
		Query query = entityManager
				.createNamedQuery("RestaurantMaster.findId");
		query.setParameter("email", email);
		List<RestaurantMaster> list=query.getResultList();
		for (int i = 0; i < list.size(); i++) {
			return list.get(i);
		}	
		return null;
	}

	

}

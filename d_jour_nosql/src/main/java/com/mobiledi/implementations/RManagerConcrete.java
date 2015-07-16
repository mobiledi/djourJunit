package com.mobiledi.implementations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonStreamContext;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.JsonGenerator.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

@Stateless
public class RManagerConcrete implements RManagerDao {
	static Logger logger = LoggerFactory.getLogger(RManagerConcrete.class);
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
			RestaurantMaster restObj = mapper.readValue(toInsert.toString(),
					RestaurantMaster.class);

			mapper = new ObjectMapper();
			RestaurantAddress restAdd = mapper.readValue(toInsert.toString(),
					RestaurantAddress.class);

			logger.info("Address info" + restAdd.getAddressLine1());
			GeometryFactory geometryFactory = new GeometryFactory();

			Coordinate coord = new Coordinate(geos.getLng().doubleValue(), geos
					.getLat().doubleValue());
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
			List<RestaurantAddress> addresses = new ArrayList<RestaurantAddress>();
			addresses.add(restAdd);
			restObj.setRestaurantAddresses(addresses);
			entityManager.persist(restObj);
			restObj.getId();
			entityManager.persist(restAdd);

			logger.info("email info" + restObj.getEmail());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean persistRAddressinfo(RestaurantMaster master,JsonNode toInsert) {
		// TODO Auto-generated method stub
		return false;
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
		hourObj.setActiveFlag(1);
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
	public boolean persistRTypesinfo(RestaurantMaster master,JsonNode toInsert) {
		boolean types[] = new boolean[4];
		types[0] = toInsert.get(Constants.ORGANIC).asBoolean();
		types[1] = toInsert.get(Constants.GLUTEN_FREE).asBoolean();
		types[2] = toInsert.get(Constants.VEG).asBoolean();
		types[3] = toInsert.get(Constants.VEGAN).asBoolean();
		
		for(int i=0;i<4;i++){
			if(types[i]){
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRAddressinfo(RestaurantMaster master,JsonNode toInsert) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRHoursinfo(RestaurantMaster master,JsonNode toInsert) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRTypesinfo(RestaurantMaster master,JsonNode toInsert) {
		// TODO Auto-generated method stub
		return false;
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
			RestaurantAddress addObj = toConvert.getRestaurantAddresses()
					.get(0);
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
			RestaurantHour hourObj = toConvert.getRestaurantHours().get(0);
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
	public ObjectNode getRBasicinfo(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectNode getRAddressinfo(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectNode getRHoursinfo(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectNode getRTypesinfo(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}

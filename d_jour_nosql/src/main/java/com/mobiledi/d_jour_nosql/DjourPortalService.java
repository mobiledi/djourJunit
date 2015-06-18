package com.mobiledi.d_jour_nosql;

import com.mobiledi.d_jour_nosql.Constants;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;

import org.codehaus.jackson.JsonNode;
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

@Stateless
public class DjourPortalService {

	/* DB details */

	
	/*  final static String DATABASE = "djour"; final static String DB_USERNAME =
	  "praks"; final static String DB_PASSWORD = ""; final static String DB_IP
	  = "127.0.0.1:5432";*/
	 

	// QA INSTANCE
	// jdbc:postgresql://djourqadb.cf7cvypppwg2.us-west-1.rds.amazonaws.com:5432/djour
	final static String DATABASE = "djour";
	final static String DB_USERNAME = "djour_portal";
	final static String DB_PASSWORD = "dj0ur";
	final static String DB_IP = "djourqadb.cf7cvypppwg2.us-west-1.rds.amazonaws.com:5432";

	final static String URL = "jdbc:postgresql://" + DB_IP + "/" + DATABASE;
	final static int INSERT_ONLY = 1;
	final static int UPDATE_ONLY = 2;
	Connection connection = null;
	static Logger logger = LoggerFactory.getLogger(DjourPortalService.class);

	public boolean connectToDB() {
		try {
			connection = DriverManager.getConnection(URL, DB_USERNAME,
					DB_PASSWORD);

			if (connection == null) {
				logger.error("FAILED TO CONNECT TO POSTGRES");
				return false;
			}

			else {
				logger.info("Connected to POSTGRES db ");
				return true;
			}

		} catch (Exception e) {
			return false;
		}

	}

	public boolean persistSignUpData(JsonNode toInsert) {

		/* EXTRACT VALUES */
		boolean success = false;
		boolean addressSuccess = false;

		if (connectToDB()) {
			String name = toInsert.get(Constants.COLUMN_NAME).asText();
			String title = toInsert.get(Constants.COLUMN_TITLE).asText();
			String email = toInsert.get(Constants.COLUMN_EMAIL).asText();
			String phone = toInsert.get(Constants.COLUMN_PHONE).asText();
			String website = toInsert.get(Constants.COLUMN_WEBSITE).asText();
			String password = toInsert.get(Constants.COLUMN_PASSWORD).asText();

			StringBuilder masterBuilder = new StringBuilder();
			masterBuilder.append("INSERT INTO ");
			masterBuilder.append(Constants.TABLE_RESTAURANT_MASTER);
			masterBuilder.append("(" + Constants.COLUMN_NAME + ",");
			masterBuilder.append(Constants.COLUMN_TITLE + ",");
			masterBuilder.append(Constants.COLUMN_EMAIL + ",");
			masterBuilder.append(Constants.COLUMN_WEBSITE + ",");
			masterBuilder.append(Constants.COLUMN_PHONE + ",");
			masterBuilder.append(Constants.COLUMN_PASSWORD + ") ");
			masterBuilder.append("VALUES (?,?,?,?,?,?)");

			logger.info("GENERATED MASTER INSERT STRING: "
					+ masterBuilder.toString());

			try {
				logger.debug("Request to add new Portal user:" + name + title
						+ email + website + phone + password);
				PreparedStatement stmt = connection.prepareStatement(
						masterBuilder.toString(),
						Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, name);
				stmt.setString(2, title);
				stmt.setString(3, email);
				stmt.setString(4, website);
				stmt.setString(5, phone);
				stmt.setString(6, password);
				logger.info("Generated basic info INSERT query: "
						+ stmt.toString());
				stmt.executeUpdate();
				success = true;
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					// ADD ADDRESS ROW
					addressSuccess = persistAddressData(toInsert, rs,
							INSERT_ONLY, 0);
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			disconnectFromDB();

		} else {

			logger.error("Cannot connect to POSTGRES db");

		}
		return (success && addressSuccess);
	}

	public JsonNode getUserDetails(JsonNode toGet) {

		if (connectToDB()) {
			int id = getMasterId(toGet.get("username").asText());
			logger.debug("Requested User details for " + toGet.get("username").asText());

			ObjectNode toreturn = new ObjectNode(JsonNodeFactory.instance);
			ObjectNode masterInfos = getMasterInfo(id);
			ObjectNode addressInfos = getAddressInfo(id);
			ObjectNode hoursInfos = getHoursInfo(id);
			ObjectNode typesInfos = getTypesInfo(id);
			toreturn.putAll(masterInfos);
			toreturn.putAll(addressInfos);
			toreturn.putAll(hoursInfos);
			toreturn.putAll(typesInfos);

			disconnectFromDB();
			return toreturn;

		} else {

			logger.error("Cannot connect to POSTGRES db");
			return null;
		}

	}

	public boolean updateSignUpData(JsonNode toUpdate) {
		boolean success = false;
		boolean addressSuccess = false;
		boolean hoursSuccess = false;
		boolean typesSuccess = false;

		if (connectToDB()) {

			int masterid = getMasterId(toUpdate.get("username").asText());
			logger.debug("Request to UPDATE User details for " + toUpdate.get("username").asText());

			String name = toUpdate.get(Constants.COLUMN_NAME).asText();
			String title = toUpdate.get(Constants.COLUMN_TITLE).asText();
			String email = toUpdate.get(Constants.COLUMN_EMAIL).asText();
			String phone = toUpdate.get(Constants.COLUMN_PHONE).asText();
			String website = toUpdate.get(Constants.COLUMN_WEBSITE).asText();
			// String password =
			// toUpdate.get(Constants.COLUMN_PASSWORD).asText();

			StringBuilder masterBuilder = new StringBuilder();
			masterBuilder.append("UPDATE ");
			masterBuilder.append(Constants.TABLE_RESTAURANT_MASTER);
			masterBuilder.append(" SET (" + Constants.COLUMN_NAME + ",");
			masterBuilder.append(Constants.COLUMN_TITLE + ",");
			masterBuilder.append(Constants.COLUMN_EMAIL + ",");
			masterBuilder.append(Constants.COLUMN_WEBSITE + ",");
			// masterBuilder.append(Constants.COLUMN_PHONE + ",");
			masterBuilder.append(Constants.COLUMN_PHONE + ") ");
			// masterBuilder.append("VALUES (?,?,?,?,?,?)");
			masterBuilder.append("= (?,?,?,?,?) WHERE "
					+ Constants.COLUMN_MASTER_ID + "=" + masterid);
			logger.info("Generated master info update String: "
					+ masterBuilder.toString());

			try {
				// logger.debug(name + title + email + website + phone
				// + password);
				PreparedStatement stmt = connection
						.prepareStatement(masterBuilder.toString());
				stmt.setString(1, name);
				stmt.setString(2, title);
				stmt.setString(3, email);
				stmt.setString(4, website);
				stmt.setString(5, phone);
				// stmt.setString(6, email);
				logger.info("Generated master info update query: " + stmt.toString());
				success = (stmt.executeUpdate() > 0 ? true : false);

				// ResultSet rs=stmt.getGeneratedKeys();
				// if (rs.next()) {
				// ADD ADDRESS and hours ROW
				addressSuccess = persistAddressData(toUpdate, null,
						UPDATE_ONLY, masterid);
				hoursSuccess = persistProfileHoursData(toUpdate, masterid);
				typesSuccess = persistTypeData(toUpdate, masterid);
				// }
				// rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			disconnectFromDB();

		} else {

			logger.error("Cannot connect to POSTGRES db");

		}
		return (success && addressSuccess && hoursSuccess && typesSuccess);

	}

	public boolean persistAddressData(JsonNode toInsert, ResultSet rs,
			int OPERATION, int id) {
		// TODO set other address inactive
		boolean success = true;
		// ADDRESS TABLE IDs
		if (OPERATION == INSERT_ONLY
				|| setActiveFlagOff(Constants.TABLE_RESTAURANT_ADDRESS, id)) {

			String name = toInsert.get(Constants.COLUMN_NAME).asText();
			String address_line1 = toInsert.get(Constants.COLUMN_ADD1).asText();
			String address_line2 = toInsert.get(Constants.COLUMN_ADD2).asText();
			String city = toInsert.get(Constants.COLUMN_CITY).asText();
			String state = toInsert.get(Constants.COLUMN_STATE).asText();
			int zip = toInsert.get(Constants.COLUMN_ZIP).asInt();
			LatLng geos = getCo_ordinates(name + ", " + address_line1 + ", "
					+ address_line2 + ", " + city + ", " + state + ", " + zip);

			StringBuilder addressBuilder = new StringBuilder();

			addressBuilder.append("INSERT INTO ");
			addressBuilder.append(Constants.TABLE_RESTAURANT_ADDRESS);
			addressBuilder.append("(" + Constants.COLUMN_MASTER_ID_ADDRESS
					+ ",");
			addressBuilder.append(Constants.COLUMN_ADD1 + ",");
			addressBuilder.append(Constants.COLUMN_ADD2 + ",");
			addressBuilder.append(Constants.COLUMN_CITY + ",");
			addressBuilder.append(Constants.COLUMN_STATE + ",");
			addressBuilder.append(Constants.COLUMN_ZIP + ",");
			addressBuilder.append(Constants.COLUMN_LAT_LNG + ",");
			// addressBuilder.append(Constants.COLUMN_LONG + ",");
			addressBuilder.append(Constants.COLUMN_ACTIVE + ",");
			addressBuilder.append(Constants.COLUMN_CREATED + ") ");
			addressBuilder
					.append("VALUES (?,?,?,?,?,?,ST_MakePoint(?, ?),?,?)");

			try {
				PreparedStatement addstmt = connection.prepareStatement(
						addressBuilder.toString(),
						Statement.RETURN_GENERATED_KEYS);
				if (OPERATION == INSERT_ONLY)
					addstmt.setInt(1, rs.getInt("id"));
				else if (OPERATION == UPDATE_ONLY)
					addstmt.setInt(1, id);
				addstmt.setString(2, address_line1);
				addstmt.setString(3, address_line2);
				addstmt.setString(4, city);
				addstmt.setString(5, state);
				addstmt.setInt(6, zip);

				addstmt.setFloat(7, geos.getLat().floatValue());
				addstmt.setFloat(8, geos.getLng().floatValue());
				addstmt.setInt(9, 1);
				addstmt.setDate(10,
						new java.sql.Date(new java.util.Date().getTime()));
				logger.info("Generated Address insert query: "
						+ addstmt.toString());
				success = success
						&& (addstmt.executeUpdate() > 0 ? true : false);

				addstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				success = false;
			}

			return success;
		}
		return false;

	}

	public boolean persistProfileHoursData(JsonNode toInsert, int masterid) {
		// TODO set other hours inactive
		boolean success = false;
		setActiveFlagOff(Constants.TABLE_RESTAURANT_HOURS, masterid);

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

		StringBuilder hoursBuilder = new StringBuilder();

		hoursBuilder.append("INSERT INTO ");
		hoursBuilder.append(Constants.TABLE_RESTAURANT_HOURS);
		hoursBuilder.append("(" + Constants.COLUMN_MASTER_ID_HOURS + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKDAY_OPENING_HOUR + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKDAY_OPENING_MINUTES + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKDAY_CLOSING_HOUR + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKDAY_CLOSING_MINUTES + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKEND_OPENING_HOUR + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKEND_OPENING_MINUTES + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKEND_CLOSING_HOUR + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKEND_CLOSING_MINUTES + ",");
		hoursBuilder.append(Constants.COLUMN_ACTIVE_HOURS + ",");
		hoursBuilder.append(Constants.COLUMN_CREATED_HOURS + ") ");
		hoursBuilder.append("VALUES (?,?,?,?,?,?,?,?,?,?,?)");
		try {
			PreparedStatement hourstmt = connection.prepareStatement(
					hoursBuilder.toString(), Statement.RETURN_GENERATED_KEYS);

			hourstmt.setInt(1, masterid);
			hourstmt.setInt(2, wd_o_h);
			hourstmt.setInt(3, wd_o_m);
			hourstmt.setInt(4, wd_c_h);
			hourstmt.setInt(5, wd_c_m);
			hourstmt.setInt(6, we_o_h);
			hourstmt.setInt(7, we_o_m);
			hourstmt.setInt(8, we_c_h);
			hourstmt.setInt(9, we_c_m);
			hourstmt.setInt(10, 1);
			hourstmt.setDate(11, new Date(new java.util.Date().getTime()));
			logger.info("Generated Hours info query : "
					+ hourstmt.toString());
			success = (hourstmt.executeUpdate() > 0 ? true : false);
			// success=true;
			hourstmt.close();
		}

		catch (SQLException e) {

			e.printStackTrace();

		}
		return success;

	}

	public boolean persistTypeData(JsonNode toInsert, int masterid) {

		boolean success = false;
		boolean types[] = new boolean[4];
		types[0] = toInsert.get(Constants.ORGANIC).asBoolean();
		types[1] = toInsert.get(Constants.GLUTEN_FREE).asBoolean();
		types[2] = toInsert.get(Constants.VEG).asBoolean();
		types[3] = toInsert.get(Constants.VEGAN).asBoolean();

		for (int i = 0; i < types.length; i++) {
			if (types[i]) {
				StringBuilder typeBuilder = new StringBuilder();
				typeBuilder.append("INSERT INTO ");
				typeBuilder.append(Constants.TABLE_RESTAURANT_TAGS);
				typeBuilder.append("(" + Constants.COLUMN_MASTER_ID_TAGS
						+ " , ");
				typeBuilder.append(Constants.COLUMN_PROFILE_TAGS + ") ");
				typeBuilder.append("VALUES (?,?)");

				try {
					PreparedStatement addstmt = connection
							.prepareStatement(typeBuilder.toString());
					addstmt.setInt(1, masterid);
					addstmt.setInt(2, i + 1);
					logger.debug("Generated Types info query : " + addstmt.toString());
					success = (addstmt.executeUpdate() > 0 ? true : false);
				} catch (SQLException e) {
					e.printStackTrace();

				}

			}

		}
		return success;
	}

	/* Check if a user is registered in the db */
	public boolean isUserRegisteredinportal(JsonNode toAuthenticate) {
		if (connectToDB()) {
			boolean toreturn = false;
			String username = toAuthenticate.get(Constants.USERNAME).asText();
			String password = toAuthenticate.get(Constants.PASSWORD).asText();
			logger.debug("Request to authenticate portal user : " +username);
			StringBuilder masterID = new StringBuilder();
			masterID.append("SELECT * FROM ");
			masterID.append(Constants.TABLE_RESTAURANT_MASTER);
			masterID.append(" WHERE " + Constants.COLUMN_EMAIL + "= '"
					+ username + "' AND " + Constants.COLUMN_PASSWORD + "='"
					+ password + "'");
			logger.info("Generated authenticate portal user query: " + masterID.toString());
			try {
				PreparedStatement masterid = connection
						.prepareStatement(masterID.toString());

				ResultSet results = masterid.executeQuery();
				while (results.next()) {
					toreturn = true;
					logger.debug("USER " + username + " is registered");
				}
				results.close();

			} catch (SQLException e) {

				e.printStackTrace();
			}

			disconnectFromDB();
			return toreturn;
		}

		else
			return false;
	}

	private int getMasterId(String key) {
		int toreturn = 0;
		// String email = toFind.get(Constants.COLUMN_EMAIL).asText();
		String email = key;

		StringBuilder masterID = new StringBuilder();
		masterID.append("SELECT * FROM ");
		masterID.append(Constants.TABLE_RESTAURANT_MASTER);
		masterID.append(" WHERE " + Constants.COLUMN_EMAIL + "= '" + email
				+ "'");
		
		try {
			PreparedStatement masterid = connection.prepareStatement(masterID
					.toString());
			logger.debug("Get Master ID query:" + masterid.toString());
			ResultSet results = masterid.executeQuery();

			while (results.next()) {
				toreturn = results.getInt("id");
				logger.debug("Got master id: " + toreturn + " for "+key );
				break;
			}
			results.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return toreturn;
	}

	private ObjectNode getMasterInfo(int id) {
		ObjectNode toreturn = new ObjectNode(JsonNodeFactory.instance);
		StringBuilder masterID = new StringBuilder();
		masterID.append("SELECT * FROM ");
		masterID.append(Constants.TABLE_RESTAURANT_MASTER);
		masterID.append(" WHERE " + Constants.COLUMN_MASTER_ID + "= " + id + "");
		logger.info("GET MASTER INFO QUERY:" + masterID.toString());
		try {
			PreparedStatement masterid = connection.prepareStatement(masterID
					.toString());
			// masterid.setString(1, email);
			ResultSet results = masterid.executeQuery();

			while (results.next()) {
				String name = results.getString(Constants.COLUMN_NAME);
				String title = results.getString(Constants.COLUMN_TITLE);
				String email = results.getString(Constants.COLUMN_EMAIL);
				String phone = results.getString(Constants.COLUMN_PHONE);
				String website = results.getString(Constants.COLUMN_WEBSITE);

				toreturn.put("name", name);
				toreturn.put("title", title);
				toreturn.put("email", email);
				toreturn.put("phone", phone);
				toreturn.put("website", website);
				break;
			}
			results.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return toreturn;

	}

	private ObjectNode getAddressInfo(int id) {
		ObjectNode toreturn = new ObjectNode(JsonNodeFactory.instance);
		StringBuilder masterID = new StringBuilder();
		masterID.append("SELECT ");
		masterID.append(Constants.COLUMN_ADD1 + ",");
		masterID.append(Constants.COLUMN_ADD2 + ",");
		masterID.append(Constants.COLUMN_CITY + ",");
		masterID.append(Constants.COLUMN_STATE + ",");
		masterID.append(Constants.COLUMN_ZIP + ",");
		masterID.append("ST_X(" + Constants.COLUMN_LAT_LNG
				+ "::geometry) AS lat,");
		masterID.append("ST_Y(" + Constants.COLUMN_LAT_LNG
				+ "::geometry) AS long");
		// masterID.append(ST_Y(geogcolumn::geometry));

		masterID.append(" FROM ");

		masterID.append(Constants.TABLE_RESTAURANT_ADDRESS);
		masterID.append(" WHERE " + Constants.COLUMN_MASTER_ID_ADDRESS + "= "
				+ id);
		masterID.append(" AND " + Constants.COLUMN_ACTIVE_HOURS + " =1");
		logger.info("GET ADDRESS WITH LAT LONG INFO ID QUERY:"
				+ masterID.toString());
		try {
			PreparedStatement masterid = connection.prepareStatement(masterID
					.toString());
			// masterid.setString(1, email);
			ResultSet results = masterid.executeQuery();

			while (results.next()) {
				String address_line1 = results.getString(Constants.COLUMN_ADD1);
				String address_line2 = results.getString(Constants.COLUMN_ADD2);
				String city = results.getString(Constants.COLUMN_CITY);
				String state = results.getString(Constants.COLUMN_STATE);
				int zip = results.getInt(Constants.COLUMN_ZIP);
				float lat = results.getFloat("lat");
				float lng = results.getFloat("long");

				toreturn.put(Constants.COLUMN_ADD1, address_line1);
				toreturn.put(Constants.COLUMN_ADD2, address_line2);
				toreturn.put(Constants.COLUMN_CITY, city);
				toreturn.put(Constants.COLUMN_STATE, state);
				toreturn.put(Constants.COLUMN_ZIP, zip);
				toreturn.put("lat", lat);
				toreturn.put("long", lng);
				break;
			}
			results.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return toreturn;

	}

	private ObjectNode getHoursInfo(int id) {
		ObjectNode toreturn = new ObjectNode(JsonNodeFactory.instance);
		StringBuilder masterID = new StringBuilder();
		masterID.append("SELECT * FROM ");
		masterID.append(Constants.TABLE_RESTAURANT_HOURS);
		masterID.append(" WHERE " + Constants.COLUMN_MASTER_ID_HOURS + "= "
				+ id);
		masterID.append(" AND " + Constants.COLUMN_ACTIVE_HOURS + " =1 ");
		logger.info("GET HOURS INFO ID QUERY:" + masterID.toString());
		try {
			PreparedStatement masterid = connection.prepareStatement(masterID
					.toString());
			// masterid.setString(1, email);
			ResultSet results = masterid.executeQuery();

			while (results.next()) {
				int WDOH = results
						.getInt(Constants.COLUMN_WEEKDAY_OPENING_HOUR);
				int WEOH = results
						.getInt(Constants.COLUMN_WEEKEND_OPENING_HOUR);
				int WDOM = results
						.getInt(Constants.COLUMN_WEEKDAY_OPENING_MINUTES);
				int WEOM = results
						.getInt(Constants.COLUMN_WEEKEND_OPENING_MINUTES);
				int WDCH = results
						.getInt(Constants.COLUMN_WEEKDAY_CLOSING_HOUR);
				int WECH = results
						.getInt(Constants.COLUMN_WEEKEND_CLOSING_HOUR);
				int WDCM = results
						.getInt(Constants.COLUMN_WEEKDAY_CLOSING_MINUTES);
				int WECM = results
						.getInt(Constants.COLUMN_WEEKEND_CLOSING_MINUTES);

				toreturn.put("weekday_opening_hour", WDOH);
				toreturn.put("weekend_opening_hour", WEOH);

				toreturn.put("weekday_opening_minutes", WDOM);
				toreturn.put("weekend_opening_minutes", WEOM);

				toreturn.put("weekday_closing_hour", WDCH);
				toreturn.put("weekend_closing_hour", WECH);

				toreturn.put("weekday_closing_minutes", WDCM);
				toreturn.put("weekend_closing_minutes", WECM);
				break;
			}
			results.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return toreturn;

	}

	private ObjectNode getTypesInfo(int id) {
		ObjectNode toreturn = new ObjectNode(JsonNodeFactory.instance);
		boolean types[] = new boolean[4];
		types[0] = false;
		types[1] = false;
		types[2] = false;
		types[3] = false;
		String type_name[] = { Constants.ORGANIC, Constants.GLUTEN_FREE,
				Constants.VEG, Constants.VEGAN };

		for (int i = 0; i < types.length; i++) {

			StringBuilder masterID = new StringBuilder();
			masterID.append("SELECT * FROM ");
			masterID.append(Constants.TABLE_RESTAURANT_TAGS);
			masterID.append(" WHERE " + Constants.COLUMN_MASTER_ID_TAGS + "= "
					+ id);
			masterID.append(" AND " + Constants.COLUMN_PROFILE_TAGS + "= "
					+ (i + 1));
			logger.info("GET TYPES INFO QUERY:" + masterID.toString());
			try {
				PreparedStatement masterid = connection
						.prepareStatement(masterID.toString());
				ResultSet results = masterid.executeQuery();

				while (results.next()) {
					types[i] = true;

				}

				// toreturn.put(type_name[i],types[i]);

			} catch (SQLException e) {
				e.printStackTrace();

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

		/*
		 * toreturn.put(Constants.ORGANIC, types[0]);
		 * toreturn.put(Constants.GLUTEN_FREE, types[1]);
		 * toreturn.put(Constants.VEG, types[2]); toreturn.put(Constants.VEGAN,
		 * types[3]);
		 */

		return toreturn;

	}

	private boolean setActiveFlagOff(String Table_Name, int id) {
		boolean success = false;

		StringBuilder updateQuery = new StringBuilder();
		updateQuery.append("UPDATE ");
		updateQuery.append(Table_Name);
		updateQuery.append(" SET " + Constants.COLUMN_ACTIVE + " = 0");
		updateQuery.append("WHERE " + Constants.COLUMN_MASTER_ID_ADDRESS + "="
				+ id);
		logger.info("GENERATED ACTIVE UPDATE STRING: "
				+ updateQuery.toString());

		try {
			PreparedStatement stmt = connection.prepareStatement(updateQuery
					.toString());
			// stmt.setString(6, email);
			logger.info("GENERATED UPDATE QUERY: " + stmt.toString());
			success = (stmt.executeUpdate() > 0 ? true : false);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;

	}

	private void disconnectFromDB() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * try {
	 * 
	 * 
	 * PreparedStatement stmt = connection.prepareStatement("INSERT INTO R");
	 * 
	 * 
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * public String getRows(){
	 * 
	 * DbSpec spec = new DbSpec(); DbSchema schema = spec.addDefaultSchema();
	 * 
	 * // add table with basic customer info DbTable restaurantTable =
	 * schema.addTable("restaurant_master"); DbColumn nameCol =
	 * restaurantTable.addColumn("name", "varchar", 255); DbColumn titleCol =
	 * restaurantTable.addColumn("title", "varchar", 255); DbColumn emailCol =
	 * restaurantTable.addColumn("email", "varchar", 100); DbColumn
	 * banner_imageCol = restaurantTable.addColumn("banner_image", "bytea",);
	 * DbColumn sturneCol = restaurantTable.addColumn("rollno", "integer",5); //
	 * restaurantTable.
	 * 
	 * 
	 * 
	 * Connection connection = null; String toreturn="";
	 * 
	 * try { connection = DriverManager.getConnection(
	 * "jdbc:postgresql://127.0.0.1:5432/djour", "praks", "");
	 * 
	 * if(connection==null){ logger.debug("FAILED TO CONNECT TO POSTGRES");
	 * return "FAILED TO CONNECT TO POSTGRES";
	 * 
	 * 
	 * } else {
	 * 
	 * logger.debug("SUCCESS  CONNECTED TO POSTGRES @ " +
	 * connection.toString()); toreturn ="SUCCESS  CONNECTED TO POSTGRES @ " +
	 * connection.toString(); }
	 * 
	 * 
	 * for (int i=20;i<30;i++) {
	 * 
	 * PreparedStatement pst1 = connection.prepareStatement(new
	 * InsertQuery(studentTable).addColumn(stunameCol,randomString(20))
	 * .addColumn(sturneCol, new Random().nextInt(i)).validate().toString()); //
	 * PreparedStatement pst1 =
	 * connection.prepareStatement("INSERT INTO students VALUES('"+
	 * randomString(10)+"',"+ new Random().nextInt(i)+")");
	 * System.out.print("Statement is: "+pst1.toString()); pst1.executeUpdate();
	 * }
	 * 
	 * PreparedStatement pst =
	 * connection.prepareStatement("SELECT * FROM students");
	 * 
	 * ResultSet rs = pst.executeQuery();
	 * 
	 * while (rs.next()) { System.out.print(rs.getString(1));
	 * System.out.print(": "); logger.debug(rs.getString(2)); } rs.close();
	 * connection.close();
	 * 
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * return toreturn; }
	 */

	public LatLng getCo_ordinates(String address) {
		logger.debug("STARTING Geo Coder");

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

	private String randomString(int len) {
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public JsonNode getAllTypes() {
		logger.debug("Testing DB Connection....Getting type info");
		ObjectNode toreturn = new ObjectNode(JsonNodeFactory.instance);
		// JsonObject toreturn= new JsonObject();;
		if (connectToDB()) {
			StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT * FROM ");
			queryString.append(Constants.TABLE_PROFILE_TAGS);
			logger.info("GET PROFILES INFO QUERY:" + queryString.toString());
			try {
				PreparedStatement query = connection
						.prepareStatement(queryString.toString());
				// masterid.setString(1, email);
				ResultSet results = query.executeQuery();
				while (results.next()) {
					String name = results.getString(Constants.COLUMN_TAG_KEY);
					String title = results
							.getString(Constants.COLUMN_TAG_VALUE);
					toreturn.put(name, title);

				}

				results.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {

			logger.error("Not Conncted to db to fetch profile tags");

		}
		System.out.println("Returning:" + toreturn.toString());
		return toreturn;

	}

}

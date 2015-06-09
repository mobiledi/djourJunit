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
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;


@Stateless
public class DjourPortalService {

	/* DB details */

	final static String DATABASE = "djour";
	final static String DB_USERNAME = "praks";
	final static String DB_PASSWORD = "";
	final static String DB_IP = "127.0.0.1:5432";
	final static String URL = "jdbc:postgresql://" + DB_IP + "/" + DATABASE;


	Connection connection = null;

	private boolean connectToDB() {
		try {
			connection = DriverManager.getConnection(URL, DB_USERNAME,
					DB_PASSWORD);

			if (connection == null) {
				System.out.println("FAILED TO CONNECT TO POSTGRES");
				return false;
			}

			else {
				System.out.println("Connected to POSTGRES db "
						+ connection.toString());
				return true;
			}

		} catch (Exception e) {
			return false;
		}

	}

	public void persistSignUpData(JsonNode toInsert) {

		/* EXTRACT VALUES */

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

			System.out.println("GENERATED MASTER INSERT STRING: "
					+ masterBuilder.toString());

			try {
				System.out.println(name + title + email + website + phone
						+ password);
				PreparedStatement stmt = connection
						.prepareStatement(masterBuilder.toString(),Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, name);
				stmt.setString(2, title);
				stmt.setString(3, email);
				stmt.setString(4, website);
				stmt.setString(5, phone);
				stmt.setString(6, password);
				System.out
						.println("GENERATED INSERT QUERY: " + stmt.toString());
				stmt.executeUpdate();	
				ResultSet rs=stmt.getGeneratedKeys();
				if (rs.next()) { 
					//ADD ADDRESS ROW 
				persistAddressData(toInsert,rs);
				}
				rs.close();
				stmt.close();
				}
			catch (SQLException e) {
				e.printStackTrace();
			}

			disconnectFromDB();

		} else {

			System.out.println("Cannot connect to POSTGRES db");

		}

	}
	
	public void persistAddressData(JsonNode toInsert,ResultSet rs){
		
		//ADDRESS TABLE IDs
		String name=toInsert.get(Constants.COLUMN_NAME).asText();
		String address_line1=toInsert.get(Constants.COLUMN_ADD1).asText();
		String address_line2=toInsert.get(Constants.COLUMN_ADD2).asText();
		String city=toInsert.get(Constants.COLUMN_CITY).asText();
		String state=toInsert.get(Constants.COLUMN_STATE).asText();
		int zip=toInsert.get(Constants.COLUMN_ZIP).asInt();
		LatLng geos= getCo_ordinates(name + ", "+
		address_line1 + ", "+address_line2 + ", "+
				city + ", "+state + ", "+zip);
		
		StringBuilder addressBuilder = new StringBuilder();
		
		addressBuilder.append("INSERT INTO ");
		addressBuilder.append(Constants.TABLE_RESTAURANT_ADDRESS);
		addressBuilder.append("(" + Constants.COLUMN_MASTER_ID + ",");
		addressBuilder.append(Constants.COLUMN_ADD1 + ",");
		addressBuilder.append(Constants.COLUMN_ADD2 + ",");
		addressBuilder.append(Constants.COLUMN_CITY + ",");
		addressBuilder.append(Constants.COLUMN_STATE + ",");
		addressBuilder.append(Constants.COLUMN_ZIP + ",");
		addressBuilder.append(Constants.COLUMN_LAT + ",");
		addressBuilder.append(Constants.COLUMN_LONG + ",");
		addressBuilder.append(Constants.COLUMN_ACTIVE + ",");
		addressBuilder.append(Constants.COLUMN_CREATED + ") ");
		addressBuilder.append("VALUES (?,?,?,?,?,?,?,?,?,?)");
	
		try {
			PreparedStatement addstmt = connection
					.prepareStatement(addressBuilder.toString(),Statement.RETURN_GENERATED_KEYS);
			addstmt.setInt(1, rs.getInt("id"));
			addstmt.setString(2, address_line1);
			addstmt.setString(3, address_line2);
			addstmt.setString(4, city);
			addstmt.setString(5, state);
			addstmt.setInt(6, zip);
			addstmt.setDouble(7, geos.getLat().doubleValue());
			addstmt.setDouble(8, geos.getLng().doubleValue());
			addstmt.setInt(9, 1);
			addstmt.setDate(10,  new Date(new java.util.Date().getTime()));
			System.out.println("GENERATED ADDRESS INSERT STRING: "
					+ addressBuilder.toString());
			addstmt.executeUpdate();
			addstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return;
		
		
	}
	
	public void persistProfileData(JsonNode toInsert,ResultSet rs){
		int wd_o_h=toInsert.get(Constants.COLUMN_WEEKDAY_OPENING_HOUR).asInt();
		int wd_o_m=toInsert.get(Constants.COLUMN_WEEKDAY_OPENING_MINUTES).asInt();
		int we_o_h=toInsert.get(Constants.COLUMN_WEEKEND_OPENING_HOUR).asInt();
		int we_o_m=toInsert.get(Constants.COLUMN_WEEKEND_OPENING_MINUTES).asInt();
		
		int wd_c_h=toInsert.get(Constants.COLUMN_WEEKDAY_CLOSING_HOUR).asInt();
		int wd_c_m=toInsert.get(Constants.COLUMN_WEEKDAY_CLOSING_MINUTES).asInt();
		int we_c_h=toInsert.get(Constants.COLUMN_WEEKEND_CLOSING_HOUR).asInt();
		int we_c_m=toInsert.get(Constants.COLUMN_WEEKEND_CLOSING_MINUTES).asInt();
		
		StringBuilder hoursBuilder = new StringBuilder();
		
		hoursBuilder.append("INSERT INTO ");
		hoursBuilder.append(Constants.TABLE_RESTAURANT_HOURS);
		hoursBuilder.append("(" + Constants.COLUMN_MASTER_ID_HOURS + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKDAY_OPENING_HOUR + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKDAY_OPENING_MINUTES + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKDAY_CLOSING_HOUR+ ",");
		hoursBuilder.append(Constants.COLUMN_WEEKDAY_CLOSING_MINUTES + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKEND_OPENING_HOUR + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKEND_OPENING_MINUTES + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKEND_CLOSING_HOUR + ",");
		hoursBuilder.append(Constants.COLUMN_WEEKEND_CLOSING_MINUTES + ",");
		hoursBuilder.append(Constants.COLUMN_ACTIVE_HOURS + ",");
		hoursBuilder.append(Constants.COLUMN_CREATED_HOURS + ") ");
		hoursBuilder.append("VALUES (?,?,?,?,?,?,?,?,?,?,?)");
		try {
			PreparedStatement hourstmt = connection
					.prepareStatement(hoursBuilder.toString(),Statement.RETURN_GENERATED_KEYS);
			
			hourstmt.setInt(1, rs.getInt("id"));
			hourstmt.setInt(2, wd_o_h);
			hourstmt.setInt(3, wd_o_m);
			hourstmt.setInt(4, wd_c_h);
			hourstmt.setInt(5, wd_c_m);
			hourstmt.setInt(6, we_o_h);
			hourstmt.setInt(7, we_o_m);
			hourstmt.setInt(8, we_c_h);
			hourstmt.setInt(9, we_c_m);
			hourstmt.setInt(10, 1);
			hourstmt.setDate(11,  new Date(new java.util.Date().getTime()));
			System.out.println("GENERATED ADDRESS INSERT STRING: "
					+ hourstmt.toString());
			hourstmt.executeUpdate();
			hourstmt.close();	
		}
		
		
		
		catch(SQLException e){
			
			e.printStackTrace();
			
		}
		
		
		
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
	 * if(connection==null){
	 * System.out.println("FAILED TO CONNECT TO POSTGRES"); return
	 * "FAILED TO CONNECT TO POSTGRES";
	 * 
	 * 
	 * } else {
	 * 
	 * System.out.println("SUCCESS  CONNECTED TO POSTGRES @ " +
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
	 * System.out.print(": "); System.out.println(rs.getString(2)); }
	 * rs.close(); connection.close();
	 * 
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * return toreturn; }
	 */

	public LatLng getCo_ordinates(String address ){
		System.out.println("STARTING GC");
		
		
		final Geocoder geocoder = new Geocoder();
		try {
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("en").getGeocoderRequest();
			GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		    List<GeocoderResult> results=geocoderResponse.getResults();
		System.out.print("REsult size "+results.size());
			for(GeocoderResult result:results){
				LatLng latLong=result.getGeometry().getLocation();
				System.out.println("Lat: "+ latLong.getLat() + " Long"+ latLong.getLng() );
				
				return latLong;
				
			}
			
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();

	String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public boolean isUserRegisteredinportal(String username, String password) {
		
		return false;
	}
	
	
	

}

package com.mobiledi.d_jour_nosql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.ejb.Stateless;

import org.codehaus.jackson.JsonNode;

@Stateless
public class DjourPortalService {

	/* DB details */

	final static String DATABASE = "djour";
	final static String DB_USERNAME = "praks";
	final static String DB_PASSWORD = "";
	final static String DB_IP = "127.0.0.1:5432";
	final static String URL = "jdbc:postgresql://" + DB_IP + "/" + DATABASE;

	/* MASTER TABLE details */
	final static String TABLE_RESTAURANT_MASTER = "restaurant_master";
	final static String COLUMN_NAME = "name";
	final static String COLUMN_TITLE = "title";
	final static String COLUMN_EMAIL = "email";
	final static String COLUMN_BANNER_IMAGE = "banner_image";
	final static String COLUMN_PHONE = "phone";
	final static String COLUMN_WEBSITE = "website";
	final static String COLUMN_PASSWORD = "password";
	
	/* ADDRESS TABLE details */
	final static String TABLE_RESTAURANT_ADDRESS = "restaurant_address";	
	final static String COLUMN_MASTER_ID = "fk_restaurant_master_id";
	final static String COLUMN_ADD1 = "address_line1";
	final static String COLUMN_ADD2 = "address_line2";
	final static String COLUMN_CITY = "city";
	final static String COLUMN_STATE = "state";
	final static String COLUMN_ZIP = "zip";
	final static String COLUMN_LAT = "lat";
	final static String COLUMN_LONG = "long";
	final static String COLUMN_ACTIVE="active_flag";
	final static String COLUMN_CREATED="create_date";
	
	/* HOURS TABLE details */
	final static String TABLE_RESTAURANT_HOURS = "restaurant_hours";
	
	
	/* TAGS TABLE details */
	final static String TABLE_RESTAURANT_TAGS = "restaurant_tags";

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

	public void persistPortalData(JsonNode toInsert) {

		/* EXTRACT VALUES */

		if (connectToDB()) {
			
			//MASTER TABLE IDs
			String name = toInsert.get(COLUMN_NAME).asText();
			String title = toInsert.get(COLUMN_TITLE).asText();
			String email = toInsert.get(COLUMN_EMAIL).asText();
			String phone = toInsert.get(COLUMN_PHONE).asText();
			String website = toInsert.get(COLUMN_WEBSITE).asText();
			String password = toInsert.get(COLUMN_PASSWORD).asText();

			StringBuilder masterBuilder = new StringBuilder();
			masterBuilder.append("INSERT INTO ");
			masterBuilder.append(TABLE_RESTAURANT_MASTER);
			masterBuilder.append("(" + COLUMN_NAME + ",");
			masterBuilder.append(COLUMN_TITLE + ",");
			masterBuilder.append(COLUMN_EMAIL + ",");
			masterBuilder.append(COLUMN_WEBSITE + ",");
			masterBuilder.append(COLUMN_PHONE + ",");
			masterBuilder.append(COLUMN_PASSWORD + ") ");
			masterBuilder.append("VALUES (?,?,?,?,?,?)");

			System.out.println("GENERATED MASTER INSERT STRING: "
					+ masterBuilder.toString());
			
			
			//HOURS TABLE IDs
			
			
			//TAGS TABLE IDs
			
			
			//END
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
				persistProfileData(toInsert,rs);
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
	
	public void persistProfileData(JsonNode toInsert,ResultSet rs){
		
		//ADDRESS TABLE IDs
		
		String address_line1=toInsert.get(COLUMN_ADD1).asText();
		String address_line2=toInsert.get(COLUMN_ADD2).asText();
		String city=toInsert.get(COLUMN_CITY).asText();
		String state=toInsert.get(COLUMN_STATE).asText();
		int zip=toInsert.get(COLUMN_ZIP).asInt();
		//long lat=toInsert.get(COLUMN_LAT).getLongValue();
		//long longit=toInsert.get(COLUMN_LONG).getLongValue();
		
		StringBuilder addressBuilder = new StringBuilder();
		
		addressBuilder.append("INSERT INTO ");
		addressBuilder.append(TABLE_RESTAURANT_ADDRESS);
		addressBuilder.append("(" + COLUMN_MASTER_ID + ",");
		addressBuilder.append(COLUMN_ADD1 + ",");
		addressBuilder.append(COLUMN_ADD2 + ",");
		addressBuilder.append(COLUMN_CITY + ",");
		addressBuilder.append(COLUMN_STATE + ",");
		addressBuilder.append(COLUMN_ZIP + ",");
		addressBuilder.append(COLUMN_LAT + ",");
		addressBuilder.append(COLUMN_LONG + ",");
		addressBuilder.append(COLUMN_ACTIVE + ",");
		addressBuilder.append(COLUMN_CREATED + ") ");
		addressBuilder.append("VALUES (?,?,?,?,?,?,?,?,?,?)");
		//addressBuilder.append("VALUES (?,?,?,?,?,?,?,?)");
		
		try {
			PreparedStatement addstmt = connection
					.prepareStatement(addressBuilder.toString(),Statement.RETURN_GENERATED_KEYS);
			addstmt.setInt(1, rs.getInt("id"));
			addstmt.setString(2, address_line1);
			addstmt.setString(3, address_line2);
			addstmt.setString(4, city);
			addstmt.setString(5, state);
			addstmt.setInt(6, zip);
			//TODO: GET CO-ORDINATES
			addstmt.setDouble(7, 0);
			addstmt.setDouble(8, 0);
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

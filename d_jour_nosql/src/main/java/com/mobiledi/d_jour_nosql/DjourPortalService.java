package com.mobiledi.d_jour_nosql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.Stateless;
@Stateless
public class DjourPortalService {
	
	
	public String getRows(){
		Connection connection = null;
		String toreturn="";
 
			try {
				connection = DriverManager.getConnection(
						"jdbc:postgresql://127.0.0.1:5432/testpostgres", "praks",
						"");
				
				if(connection==null){
					System.out.println("FAILED TO CONNECT TO POSTGRES");
					return "FAILED TO CONNECT TO POSTGRES";
					
					
				}
				else {
					
					System.out.println("SUCCESS  CONNECTED TO POSTGRES @ " + connection.toString());
					toreturn ="SUCCESS  CONNECTED TO POSTGRES @ " + connection.toString();
				}
				
	           PreparedStatement pst = connection.prepareStatement("SELECT * FROM students");
	           ResultSet rs = pst.executeQuery();

	            while (rs.next()) {
	                System.out.print(rs.getString(1));
	                System.out.print(": ");
	                System.out.println(rs.getString(2));
	            }
	            rs.close();
	            connection.close();
		
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
 	
		return toreturn;
	}  

}

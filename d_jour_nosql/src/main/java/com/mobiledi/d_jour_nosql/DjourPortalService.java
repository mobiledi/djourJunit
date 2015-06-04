package com.mobiledi.d_jour_nosql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.ejb.Stateless;

import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
@Stateless
public class DjourPortalService {
	
	
	public String getRows(){
		
		DbSpec spec = new DbSpec();
	    DbSchema schema = spec.addDefaultSchema();

	    // add table with basic customer info
	    DbTable studentTable = schema.addTable("students");
	    DbColumn stunameCol = studentTable.addColumn("name", "varchar", 20);
	    DbColumn sturneCol = studentTable.addColumn("rollno", "integer",5);
		
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
				
				
				for (int i=20;i<30;i++)
				{
					
					PreparedStatement pst1 = connection.prepareStatement(new InsertQuery(studentTable).addColumn(stunameCol,randomString(20))
							.addColumn(sturneCol, new Random().nextInt(i)).validate().toString());
		          // PreparedStatement pst1 = connection.prepareStatement("INSERT INTO students VALUES('"+ randomString(10)+"',"+ new Random().nextInt(i)+")");
					 System.out.print("Statement is: "+pst1.toString());
					pst1.executeUpdate();
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

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();

	String randomString( int len ) 
	{
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
	
	
	
	
}

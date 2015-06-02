package com.mobiledi.d_jour_nosql;

import java.net.UnknownHostException;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.codehaus.jackson.JsonNode;

import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

@Stateless
//@Singleton
public class DjourService {
	private static final String APP_USER_COLLECTION_NAME = "AppUsers";
	private static final String PORTAL_USER_COLLECTION_NAME = "PortalUsers";
	private static final String DATABASE = "djour_db";
	private static final String HOST = "localhost";
	private static final int PORT = 27017;
	private static MongoClient CLIENT; 
	private DBCollection collections;
	
	public DjourService() {
		System.out.println("Opeining Client..");	
			//collections = connectToDB( HOST, PORT, COLLECTION_NAME, DATABASE);
			
	}

	public static String sayHello(String name){
		

		return "D'jour says Hello to " + name + "!";	
}



	/* saves the record into the db 

	public void persistAppUser(JsonNode add) throws MongoException {
		collections=connectToDB(HOST, PORT, APP_USER_COLLECTION_NAME, DATABASE);
		collections.insert((DBObject) JSON.parse(add.toString()));
		closeConnection();
	}
	
	public void persistPortalData(JsonNode add) throws MongoException {
		collections=connectToDB(HOST, PORT, PORTAL_USER_COLLECTION_NAME, DATABASE);
		collections.insert((DBObject) JSON.parse(add.toString()));
		closeConnection();
	}*/
/*	public List<DBObject> getData() {
		collections = connectToDB( HOST, PORT, COLLECTION_NAME, DATABASE);
		DBCursor cursor=collections.find();
		
		List<DBObject> toreturn= cursor.toArray();
		
		closeConnection();
		return toreturn;
	}

@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		//closeConnection();
	}

*/	

/*	public DBCollection connectToDB( String host, int port,
		String collectionName, String dbname) {
		DB db = CLIENT.getDB(dbname);	
		return db.getCollection(collectionName);

	}
	public DBCollection connectToDB(String host, int port,
			String collectionName, String dbname) {
		try {
			//if(CLIENT==null)
			CLIENT = new MongoClient(host, port);
			DB db = CLIENT.getDB(dbname);
			System.out.println("Client open now..");
			return db.getCollection(collectionName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return CLIENT.getDB(dbname).getCollection(collectionName);
		}

	}

	private void closeConnection() {
		System.out.println("Closing Client..");
		if (DjourService.CLIENT != null) {
			DjourService.CLIENT.close();
			System.out.println("Connection Closed..");
		}
	}
	*/
	

}

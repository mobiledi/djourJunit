package com.mobiledi.djourDAO;

import java.net.UnknownHostException;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.healthmarketscience.sqlbuilder.dbspec.basic.DbObject;
import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;
import com.mongodb.util.JSON;

@Stateless
//@Singleton
public class DjourAppDAO {
	private static final String APP_USER_COLLECTION_NAME = "AppUsers";
	private static final String DATABASE = "djour_app_db";
	private static final String HOST = "localhost";
	private static final int PORT = 27017;
	private static MongoClient CLIENT; 
	private DBCollection collections;
	static Logger logger=LoggerFactory.getLogger(DjourAppDAO.class);
	
	public DjourAppDAO() {
		}

	public static String sayHello(String name){
		return "D\'jour says Hello to " + name + " !";	
}
	/* saves the record into the db */

	public boolean persistAppUser(JsonNode add) throws MongoException {
		logger.info("New app User added: " + ((DBObject) JSON.parse(add.toString())).toString());
		
		collections=connectToDB(HOST, PORT, APP_USER_COLLECTION_NAME, DATABASE);
		DBObject toInsert=(DBObject) JSON.parse(add.toString());		
		collections.insert(toInsert);	
		
		closeConnection();
return true;
	}
	
/*	public List<DBObject> getData() {
		collections = connectToDB( HOST, PORT, COLLECTION_NAME, DATABASE);
		DBCursor cursor=collections.find();
		
		List<DBObject> toreturn= cursor.toArray();
		
		closeConnection();
		return toreturn;
	}

*/	

	public DBCollection connectToDB(String host, int port,
			String collectionName, String dbname) {
		try {
			//if(CLIENT==null)
			CLIENT = new MongoClient(host, port);
			DB db = CLIENT.getDB(dbname);
			return db.getCollection(collectionName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return CLIENT.getDB(dbname).getCollection(collectionName);
		}

	}
	
	public boolean isUserRegisteredinApp(String username, String password) {
		logger.info("Request to authenticate " + username );
		collections=connectToDB(HOST, PORT, APP_USER_COLLECTION_NAME, DATABASE);
		DBObject getUser= QueryBuilder.start("username").is(username).and("password").is(password).get();
		DBCursor cursor=collections.find(getUser);
		logger.info("User Registered?: "+cursor.count());
		if(cursor.count()>0)	
		{		closeConnection();
			return true;
	}else{
			closeConnection();
			return false;}
	}

	private void closeConnection() {
		logger.debug("Closing Client..");
		if (DjourAppDAO.CLIENT != null) {
			DjourAppDAO.CLIENT.close();
			logger.debug("Connection Closed..");
		}
	}


	
	
	

}

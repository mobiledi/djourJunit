package com.mobiledi.utils;

public final class Constants {
	
	public final static String USERNAME = "username";
	public final static String PASSWORD = "password";
	
	
	

	/* MASTER TABLE details */
	public final static String TABLE_RESTAURANT_MASTER = "restaurant_master";
	public final static String COLUMN_MASTER_ID="id";
	public final static String COLUMN_NAME = "name";
	public final static String COLUMN_TITLE = "title";
	public  final static String COLUMN_EMAIL = "email";
	public final static String COLUMN_BANNER_IMAGE = "banner_image";
	public final static String COLUMN_PHONE = "phone";
	public final static String COLUMN_WEBSITE = "website";
	public final static String COLUMN_PASSWORD = "password";
	
	/* ADDRESS TABLE details */
	public final static String TABLE_RESTAURANT_ADDRESS = "restaurant_address";	
	public final static String COLUMN_MASTER_ID_ADDRESS = "fk_restaurant_master_id";
	public final static String COLUMN_ADD1 = "address_line1";
	public final static String COLUMN_ADD2 = "address_line2";
	public final static String COLUMN_CITY = "city";
	public final static String COLUMN_STATE = "state";
	public final static String COLUMN_ZIP = "zip";
	public final static String COLUMN_LAT_LNG = "geom";
	//public final static String COLUMN_LONG = "long";
	public final static String COLUMN_ACTIVE="active_flag";
	public final static String COLUMN_CREATED="create_date";
	public final static String COLUMN_LATITUDE = "latitude";
	public final static String COLUMN_LONGITUDE = "longitude";
	
	/* HOURS TABLE details */
	public final static String TABLE_RESTAURANT_HOURS = "restaurant_hours";
	public final static String COLUMN_MASTER_ID_HOURS = "fk_restaurant_master_id";
	public final static String COLUMN_WEEKDAY_OPENING_HOUR = "weekday_opening_hour";
	public final static String COLUMN_WEEKDAY_OPENING_MINUTES = "weekday_opening_minutes";
	public final static String COLUMN_WEEKDAY_CLOSING_HOUR="weekday_closing_hour";
	public final static String COLUMN_WEEKDAY_CLOSING_MINUTES ="weekday_closing_minutes";
	public final static String COLUMN_WEEKEND_OPENING_HOUR = "weekend_opening_hour";
	public final static String COLUMN_WEEKEND_OPENING_MINUTES = "weekend_opening_minutes";
	public final static String COLUMN_WEEKEND_CLOSING_HOUR="weekend_closing_hour";
	public final static String COLUMN_WEEKEND_CLOSING_MINUTES ="weekend_closing_minutes";
	public final static String COLUMN_ACTIVE_HOURS="active_flag";
	public final static String COLUMN_CREATED_HOURS="create_datetime";
	
	
	/*PROFILE TAGS*/
	public final static String TABLE_PROFILE_TAGS = "profile_tags";
	public final static String COLUMN_ID_TAGS = "id";
	public final static String COLUMN_TAG_KEY = "tag_key";
	public final static String COLUMN_TAG_VALUE = "tag_value";
	
	
	/* TAGS TABLE details */
	public final static String TABLE_RESTAURANT_TAGS = "restaurant_tags";
	public final static String COLUMN_MASTER_ID_TAGS = "fk_restaurant_id";
	public final static String COLUMN_PROFILE_TAGS = "fk_profile_tags_id";
	
	public final static String ORGANIC = "org";
	public final static String GLUTEN_FREE = "gf";
	public final static String VEG = "veg";
	public final static String VEGAN = "vgn";
	
	/* RESTAURANT FEED ITEMS*/
	public final static String TABLE_RESTAURANT_FEED_ITEMS = "restaurant_feed_items";
	public final static String COLUMN_ID_FEED = "id";
	public final static String COLUMN_MASTER_ID_FEED = "fk_restaurant_master";
	public final static String COLUMN_FEED_TITLE = "feed_title";
	public final static String COLUMN_FEED_DESCRIPTION = "feed_description";
	public final static String COLUMN_FEED_IMAGE = "feed_image";
	public final static String COLUMN_FEED_SEQUENCE_NUMBER = "fee_sequence_number";
	public final static String COLUMN_FEED_CREATE_DATE = "create_date";
	public final static String COLUMN_FEED_UPDATE_TIME = "update_date";
}

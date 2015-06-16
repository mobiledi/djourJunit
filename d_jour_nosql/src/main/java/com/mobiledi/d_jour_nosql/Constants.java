package com.mobiledi.d_jour_nosql;

public final class Constants {
	
	final static String USERNAME = "username";
	final static String PASSWORD = "password";
	
	
	

	/* MASTER TABLE details */
	final static String TABLE_RESTAURANT_MASTER = "restaurant_master";
	final static String COLUMN_MASTER_ID="id";
	final static String COLUMN_NAME = "name";
	final static String COLUMN_TITLE = "title";
	final static String COLUMN_EMAIL = "email";
	final static String COLUMN_BANNER_IMAGE = "banner_image";
	final static String COLUMN_PHONE = "phone";
	final static String COLUMN_WEBSITE = "website";
	final static String COLUMN_PASSWORD = "password";
	
	/* ADDRESS TABLE details */
	final static String TABLE_RESTAURANT_ADDRESS = "restaurant_address";	
	final static String COLUMN_MASTER_ID_ADDRESS = "fk_restaurant_master_id";
	final static String COLUMN_ADD1 = "address_line1";
	final static String COLUMN_ADD2 = "address_line2";
	final static String COLUMN_CITY = "city";
	final static String COLUMN_STATE = "state";
	final static String COLUMN_ZIP = "zip";
	final static String COLUMN_LAT_LNG = "lat_lng";
	//final static String COLUMN_LONG = "long";
	final static String COLUMN_ACTIVE="active_flag";
	final static String COLUMN_CREATED="create_date";
	
	/* HOURS TABLE details */
	final static String TABLE_RESTAURANT_HOURS = "restaurant_hours";
	final static String COLUMN_MASTER_ID_HOURS = "fk_restaurant_master_id";
	final static String COLUMN_WEEKDAY_OPENING_HOUR = "weekday_opening_hour";
	final static String COLUMN_WEEKDAY_OPENING_MINUTES = "weekday_opening_minutes";
	final static String COLUMN_WEEKDAY_CLOSING_HOUR="weekday_closing_hour";
	final static String COLUMN_WEEKDAY_CLOSING_MINUTES ="weekday_closing_minutes";
	final static String COLUMN_WEEKEND_OPENING_HOUR = "weekend_opening_hour";
	final static String COLUMN_WEEKEND_OPENING_MINUTES = "weekend_opening_minutes";
	final static String COLUMN_WEEKEND_CLOSING_HOUR="weekend_closing_hour";
	final static String COLUMN_WEEKEND_CLOSING_MINUTES ="weekend_closing_minutes";
	final static String COLUMN_ACTIVE_HOURS="active_flag";
	final static String COLUMN_CREATED_HOURS="create_datetime";
	
	
	/*PROFILE TAGS*/
	final static String TABLE_PROFILE_TAGS = "profile_tags";
	final static String COLUMN_ID_TAGS = "id";
	final static String COLUMN_TAG_KEY = "tag_key";
	final static String COLUMN_TAG_VALUE = "tag_value";
	
	
	/* TAGS TABLE details */
	final static String TABLE_RESTAURANT_TAGS = "restaurant_tags";
	final static String COLUMN_MASTER_ID_TAGS = "fk_restaurant_id";
	final static String COLUMN_PROFILE_TAGS = "fk_profile_tags_id";
	
	final static String ORGANIC = "org";
	final static String GLUTEN_FREE = "gf";
	final static String VEG = "veg";
	final static String VEGAN = "vgn";
	
	/* RESTAURANT FEED ITEMS*/
	final static String TABLE_RESTAURANT_FEED_ITEMS = "restaurant_feed_items";
	final static String COLUMN_ID_FEED = "id";
	final static String COLUMN_MASTER_ID_FEED = "fk_restaurant_master";
	final static String COLUMN_FEED_TITLE = "feed_title";
	final static String COLUMN_FEED_DESCRIPTION = "feed_description";
	final static String COLUMN_FEED_IMAGE = "feed_image";
	final static String COLUMN_FEED_SEQUENCE_NUMBER = "fee_sequence_number";
	final static String COLUMN_FEED_CREATE_DATE = "create_date";
	final static String COLUMN_FEED_UPDATE_TIME = "update_date";
}

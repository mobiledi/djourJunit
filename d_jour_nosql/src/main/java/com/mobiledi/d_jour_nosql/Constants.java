package com.mobiledi.d_jour_nosql;

public final class Constants {
	
	final public static String USERNAME = "username";
	final public static String PASSWORD = "password";
	
	
	

	/* MASTER TABLE details */
	final public static String TABLE_RESTAURANT_MASTER = "restaurant_master";
	final public static String COLUMN_MASTER_ID="id";
	final public static String COLUMN_NAME = "name";
	final public static String COLUMN_TITLE = "title";
	final public static String COLUMN_EMAIL = "email";
	final public static String COLUMN_BANNER_IMAGE = "banner_image";
	final public static String COLUMN_PHONE = "phone";
	final public static String COLUMN_WEBSITE = "website";
	final public static String COLUMN_PASSWORD = "password";
	
	/* ADDRESS TABLE details */
	final public static String TABLE_RESTAURANT_ADDRESS = "restaurant_address";	
	final public static String COLUMN_MASTER_ID_ADDRESS = "fk_restaurant_master_id";
	final public static String COLUMN_ADD1 = "address_line1";
	final public static String COLUMN_ADD2 = "address_line2";
	final public static String COLUMN_CITY = "city";
	final public static String COLUMN_STATE = "state";
	final public static String COLUMN_ZIP = "zip";
	final public static String COLUMN_LAT = "lat";
	final public static String COLUMN_LONG = "long";
	final public static String COLUMN_ACTIVE="active_flag";
	final public static String COLUMN_CREATED="create_date";
	
	/* HOURS TABLE details */
	final public static String TABLE_RESTAURANT_HOURS = "restaurant_hours";
	final public static String COLUMN_MASTER_ID_HOURS = "fk_restaurant_master_id";
	final public static String COLUMN_WEEKDAY_OPENING_HOUR = "weekday_opening_hour";
	final public static String COLUMN_WEEKDAY_OPENING_MINUTES = "weekday_opening_minutes";
	final public static String COLUMN_WEEKDAY_CLOSING_HOUR="weekday_closing_hour";
	final public static String COLUMN_WEEKDAY_CLOSING_MINUTES ="weekday_closing_minutes";
	final public static String COLUMN_WEEKEND_OPENING_HOUR = "weekend_opening_hour";
	final public static String COLUMN_WEEKEND_OPENING_MINUTES = "weekend_opening_minutes";
	final public static String COLUMN_WEEKEND_CLOSING_HOUR="weekend_closing_hour";
	final public static String COLUMN_WEEKEND_CLOSING_MINUTES ="weekend_closing_minutes";
	final public static String COLUMN_ACTIVE_HOURS="active_flag";
	final public static String COLUMN_CREATED_HOURS="create_datetime";
	
	
	/*PROFILE TAGS*/
	final public static String TABLE_PROFILE_TAGS = "profile_tags";
	final public static String COLUMN_ID_TAGS = "id";
	final public static String COLUMN_TAG_KEY = "tag_key";
	final public static String COLUMN_TAG_VALUE = "tag_value";
	
	
	/* TAGS TABLE details */
	final public static String TABLE_RESTAURANT_TAGS = "restaurant_tags";
	final public static String COLUMN_MASTER_ID_TAGS = "fk_restaurant_id";
	final public static String COLUMN_PROFILE_TAGS = "fk_profile_tags_id";
	
	/* RESTAURANT FEED ITEMS*/
	final public static String TABLE_RESTAURANT_FEED_ITEMS = "restaurant_feed_items";
	final public static String COLUMN_ID_FEED = "id";
	final public static String COLUMN_MASTER_ID_FEED = "fk_restaurant_master";
	final public static String COLUMN_FEED_TITLE = "feed_title";
	final public static String COLUMN_FEED_DESCRIPTION = "feed_description";
	final public static String COLUMN_FEED_IMAGE = "feed_image";
	final public static String COLUMN_FEED_SEQUENCE_NUMBER = "fee_sequence_number";
	final public static String COLUMN_FEED_CREATE_DATE = "create_date";
	final public static String COLUMN_FEED_UPDATE_TIME = "update_date";
}

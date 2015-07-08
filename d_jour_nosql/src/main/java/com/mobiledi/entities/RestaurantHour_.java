package com.mobiledi.entities;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-07-08T09:29:22.365+0530")
@StaticMetamodel(RestaurantHour.class)
public class RestaurantHour_ {
	public static volatile SingularAttribute<RestaurantHour, Integer> id;
	public static volatile SingularAttribute<RestaurantHour, Integer> activeFlag;
	public static volatile SingularAttribute<RestaurantHour, Timestamp> createDatetime;
	public static volatile SingularAttribute<RestaurantHour, Integer> weekdayClosingHour;
	public static volatile SingularAttribute<RestaurantHour, Integer> weekdayClosingMinutes;
	public static volatile SingularAttribute<RestaurantHour, Integer> weekdayOpeningHour;
	public static volatile SingularAttribute<RestaurantHour, Integer> weekdayOpeningMinutes;
	public static volatile SingularAttribute<RestaurantHour, Integer> weekendClosingHour;
	public static volatile SingularAttribute<RestaurantHour, Integer> weekendClosingMinutes;
	public static volatile SingularAttribute<RestaurantHour, Integer> weekendOpeningHour;
	public static volatile SingularAttribute<RestaurantHour, Integer> weekendOpeningMinutes;
	public static volatile SingularAttribute<RestaurantHour, RestaurantMaster> restaurantMaster;
}

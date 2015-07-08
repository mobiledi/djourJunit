package com.mobiledi.entities;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.postgis.PGgeometry;

@Generated(value="Dali", date="2015-07-08T09:33:01.316+0530")
@StaticMetamodel(RestaurantAddress.class)
public class RestaurantAddress_ {
	public static volatile SingularAttribute<RestaurantAddress, Integer> id;
	public static volatile SingularAttribute<RestaurantAddress, Integer> activeFlag;
	public static volatile SingularAttribute<RestaurantAddress, String> addressLine1;
	public static volatile SingularAttribute<RestaurantAddress, String> addressLine2;
	public static volatile SingularAttribute<RestaurantAddress, String> city;
	public static volatile SingularAttribute<RestaurantAddress, Timestamp> createDate;
	public static volatile SingularAttribute<RestaurantAddress, PGgeometry> latLng;
	public static volatile SingularAttribute<RestaurantAddress, Double> latitude;
	public static volatile SingularAttribute<RestaurantAddress, Double> longitude;
	public static volatile SingularAttribute<RestaurantAddress, String> state;
	public static volatile SingularAttribute<RestaurantAddress, Integer> zip;
	public static volatile SingularAttribute<RestaurantAddress, RestaurantMaster> restaurantMaster;
}

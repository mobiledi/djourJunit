package com.mobiledi.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-07-08T09:33:01.387+0530")
@StaticMetamodel(RestaurantMaster.class)
public class RestaurantMaster_ {
	public static volatile SingularAttribute<RestaurantMaster, Integer> id;
	public static volatile SingularAttribute<RestaurantMaster, byte[]> bannerImage;
	public static volatile SingularAttribute<RestaurantMaster, String> email;
	public static volatile SingularAttribute<RestaurantMaster, String> name;
	public static volatile SingularAttribute<RestaurantMaster, String> password;
	public static volatile SingularAttribute<RestaurantMaster, String> phone;
	public static volatile SingularAttribute<RestaurantMaster, String> title;
	public static volatile SingularAttribute<RestaurantMaster, String> website;
	public static volatile ListAttribute<RestaurantMaster, RestaurantAddress> restaurantAddresses;
	public static volatile ListAttribute<RestaurantMaster, RestaurantFeedItem> restaurantFeedItems;
	public static volatile ListAttribute<RestaurantMaster, RestaurantHour> restaurantHours;
	public static volatile ListAttribute<RestaurantMaster, ProfileTag> profileTags;
}

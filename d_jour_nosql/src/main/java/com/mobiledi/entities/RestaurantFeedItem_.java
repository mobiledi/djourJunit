package com.mobiledi.entities;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-07-08T09:29:22.363+0530")
@StaticMetamodel(RestaurantFeedItem.class)
public class RestaurantFeedItem_ {
	public static volatile SingularAttribute<RestaurantFeedItem, Integer> id;
	public static volatile SingularAttribute<RestaurantFeedItem, Timestamp> createDate;
	public static volatile SingularAttribute<RestaurantFeedItem, Integer> feeSequenceNumber;
	public static volatile SingularAttribute<RestaurantFeedItem, String> feedDescription;
	public static volatile SingularAttribute<RestaurantFeedItem, byte[]> feedImage;
	public static volatile SingularAttribute<RestaurantFeedItem, String> feedTitle;
	public static volatile SingularAttribute<RestaurantFeedItem, Timestamp> updateDate;
	public static volatile SingularAttribute<RestaurantFeedItem, RestaurantMaster> restaurantMaster;
}

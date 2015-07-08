package com.mobiledi.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-07-08T09:29:22.361+0530")
@StaticMetamodel(ProfileTag.class)
public class ProfileTag_ {
	public static volatile SingularAttribute<ProfileTag, Integer> id;
	public static volatile SingularAttribute<ProfileTag, String> tagKey;
	public static volatile SingularAttribute<ProfileTag, String> tagValue;
	public static volatile ListAttribute<ProfileTag, RestaurantMaster> restaurantMasters;
}

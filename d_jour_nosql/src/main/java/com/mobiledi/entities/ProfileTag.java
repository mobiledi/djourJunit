package com.mobiledi.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the profile_tags database table.
 * 
 */
@Entity
@Table(name="profile_tags")
@NamedQuery(name="ProfileTag.findAll", query="SELECT p FROM ProfileTag p")
public class ProfileTag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="tag_key", nullable=false, length=10)
	private String tagKey;

	@Column(name="tag_value", nullable=false, length=20)
	private String tagValue;

	//bi-directional many-to-many association to RestaurantMaster
	@ManyToMany(mappedBy="profileTags")
	private List<RestaurantMaster> restaurantMasters;

	public ProfileTag() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTagKey() {
		return this.tagKey;
	}

	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}

	public String getTagValue() {
		return this.tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public List<RestaurantMaster> getRestaurantMasters() {
		return this.restaurantMasters;
	}

	public void setRestaurantMasters(List<RestaurantMaster> restaurantMasters) {
		this.restaurantMasters = restaurantMasters;
	}

}
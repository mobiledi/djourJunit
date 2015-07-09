package com.mobiledi.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the restaurant_master database table.
 * 
 */
@Entity
@Table(name="restaurant_master")
@NamedQuery(name="RestaurantMaster.findAll", query="SELECT r FROM RestaurantMaster r")
public class RestaurantMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="banner_image")
	private byte[] bannerImage;

	@Column(nullable=false, length=100)
	private String email;

	@Column(nullable=false, length=255)
	private String name;

	@Column(nullable=false, length=255)
	private String password;

	@Column(nullable=false, length=2147483647)
	private String phone;

	@Column(length=255)
	private String title;

	@Column(length=255)
	private String website;

	//bi-directional many-to-one association to RestaurantAddress
	@OneToMany(mappedBy="restaurantMaster", fetch=FetchType.LAZY)
	private List<RestaurantAddress> restaurantAddresses;

	//bi-directional many-to-one association to RestaurantFeedItem
	@OneToMany(mappedBy="restaurantMaster")
	private List<RestaurantFeedItem> restaurantFeedItems;

	//bi-directional many-to-one association to RestaurantHour
	@OneToMany(mappedBy="restaurantMaster")
	private List<RestaurantHour> restaurantHours;

	//bi-directional many-to-many association to ProfileTag
	@ManyToMany
	@JoinTable(
		name="restaurant_tags"
		, joinColumns={
			@JoinColumn(name="fk_restaurant_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="fk_profile_tags_id")
			}
		)
	private List<ProfileTag> profileTags;

	public RestaurantMaster() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getBannerImage() {
		return this.bannerImage;
	}

	public void setBannerImage(byte[] bannerImage) {
		this.bannerImage = bannerImage;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public List<RestaurantAddress> getRestaurantAddresses() {
		return this.restaurantAddresses;
	}

	public void setRestaurantAddresses(List<RestaurantAddress> restaurantAddresses) {
		this.restaurantAddresses = restaurantAddresses;
	}

	public RestaurantAddress addRestaurantAddress(RestaurantAddress restaurantAddress) {
		getRestaurantAddresses().add(restaurantAddress);
		restaurantAddress.setRestaurantMaster(this);

		return restaurantAddress;
	}

	public RestaurantAddress removeRestaurantAddress(RestaurantAddress restaurantAddress) {
		getRestaurantAddresses().remove(restaurantAddress);
		restaurantAddress.setRestaurantMaster(null);

		return restaurantAddress;
	}

	public List<RestaurantFeedItem> getRestaurantFeedItems() {
		return this.restaurantFeedItems;
	}

	public void setRestaurantFeedItems(List<RestaurantFeedItem> restaurantFeedItems) {
		this.restaurantFeedItems = restaurantFeedItems;
	}

	public RestaurantFeedItem addRestaurantFeedItem(RestaurantFeedItem restaurantFeedItem) {
		getRestaurantFeedItems().add(restaurantFeedItem);
		restaurantFeedItem.setRestaurantMaster(this);

		return restaurantFeedItem;
	}

	public RestaurantFeedItem removeRestaurantFeedItem(RestaurantFeedItem restaurantFeedItem) {
		getRestaurantFeedItems().remove(restaurantFeedItem);
		restaurantFeedItem.setRestaurantMaster(null);

		return restaurantFeedItem;
	}

	public List<RestaurantHour> getRestaurantHours() {
		return this.restaurantHours;
	}

	public void setRestaurantHours(List<RestaurantHour> restaurantHours) {
		this.restaurantHours = restaurantHours;
	}

	public RestaurantHour addRestaurantHour(RestaurantHour restaurantHour) {
		getRestaurantHours().add(restaurantHour);
		restaurantHour.setRestaurantMaster(this);

		return restaurantHour;
	}

	public RestaurantHour removeRestaurantHour(RestaurantHour restaurantHour) {
		getRestaurantHours().remove(restaurantHour);
		restaurantHour.setRestaurantMaster(null);

		return restaurantHour;
	}

	public List<ProfileTag> getProfileTags() {
		return this.profileTags;
	}

	public void setProfileTags(List<ProfileTag> profileTags) {
		this.profileTags = profileTags;
	}

}
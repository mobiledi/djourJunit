package com.mobiledi.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.sql.Timestamp;


/**
 * The persistent class for the restaurant_feed_items database table.
 * 
 */
@Entity
@Table(name="restaurant_feed_items")
@NamedQuery(name="RestaurantFeedItem.findAll", query="SELECT r FROM RestaurantFeedItem r")
@JsonIgnoreProperties(ignoreUnknown=true)
public class RestaurantFeedItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="create_date")
	private Timestamp createDate;

	@Column(name="fee_sequence_number")
	private Integer feeSequenceNumber;

	@Column(name="feed_description")
	private String feedDescription;

	@Column(name="feed_image")
	private byte[] feedImage;

	@Column(name="feed_title")
	private String feedTitle;

	@Column(name="update_date")
	private Timestamp updateDate;

	//bi-directional many-to-one association to RestaurantMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fk_restaurant_master")
	private RestaurantMaster restaurantMaster;

	public RestaurantFeedItem() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Integer getFeeSequenceNumber() {
		return this.feeSequenceNumber;
	}

	public void setFeeSequenceNumber(Integer feeSequenceNumber) {
		this.feeSequenceNumber = feeSequenceNumber;
	}

	public String getFeedDescription() {
		return this.feedDescription;
	}

	public void setFeedDescription(String feedDescription) {
		this.feedDescription = feedDescription;
	}

	public byte[] getFeedImage() {
		return this.feedImage;
	}

	public void setFeedImage(byte[] feedImage) {
		this.feedImage = feedImage;
	}

	public String getFeedTitle() {
		return this.feedTitle;
	}

	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public RestaurantMaster getRestaurantMaster() {
		return this.restaurantMaster;
	}

	public void setRestaurantMaster(RestaurantMaster restaurantMaster) {
		this.restaurantMaster = restaurantMaster;
	}

}
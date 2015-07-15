package com.mobiledi.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

import java.sql.Timestamp;


/**
 * The persistent class for the restaurant_address database table.
 * 
 */
@Entity
@Table(name="restaurant_address")
@NamedQuery(name="RestaurantAddress.findAll", query="SELECT r FROM RestaurantAddress r")

@JsonIgnoreProperties(ignoreUnknown=true)
public class RestaurantAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="active_flag")
	private Integer activeFlag;

	@Column(name="address_line1")
	private String addressLine1;

	@Column(name="address_line2")
	private String addressLine2;

	private String city;

	@Column(name="create_date")
	private Timestamp createDate;

	@Column(columnDefinition = "Geometry", nullable = true)
	@Type(type = "org.hibernate.spatial.GeometryType")
	private Geometry geom;

	private double latitude;

	private double longitude;

	private String state;

	private Integer zip;

	//bi-directional many-to-one association to RestaurantMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fk_restaurant_master_id")
	private RestaurantMaster restaurantMaster;

	public RestaurantAddress() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(Integer activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return this.addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Geometry getGeom() {
		return this.geom;
	}

	public void setGeom(Geometry geom) {
		this.geom = geom;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getZip() {
		return this.zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

	public RestaurantMaster getRestaurantMaster() {
		return this.restaurantMaster;
	}

	public void setRestaurantMaster(RestaurantMaster restaurantMaster) {
		this.restaurantMaster = restaurantMaster;
	}

}
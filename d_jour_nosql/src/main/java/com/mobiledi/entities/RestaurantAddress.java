package com.mobiledi.entities;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;
//import org.postgis.PGgeometry;


import org.postgis.PGgeometry;
import org.postgresql.util.PGobject;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

import java.sql.Timestamp;


/*//**
 * The persistent class for the restaurant_address database table.
 * 
 *//*/*/
@Entity
@Table(name="restaurant_address")
@NamedQuery(name="RestaurantAddress.findAll", query="SELECT r FROM RestaurantAddress r")

public class RestaurantAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="active_flag", nullable=false)
	private Integer activeFlag;

	@Column(name="address_line1", nullable=false, length=255)
	private String addressLine1;

	@Column(name="address_line2", length=255)
	private String addressLine2;

	@Column(length=100)
	private String city;

	@Column(name="create_date")
	private Timestamp createDate;

	@Column(name="lat_lng",columnDefinition="Geometry")
	//@Type(type="org.hibernate.spatial.GeometryType")
	private  String latLng;

	private double latitude;

	private double longitude;

	@Column(nullable=false, length=100)
	private String state;

	@Column(nullable=false)
	private Integer zip;

	//bi-directional many-to-one association to RestaurantMaster
	@ManyToOne
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

	public  String getLatLng() {
		return this.latLng;
	}

	public void setLatLng(String latLng) {
		//Geometry geom= new WKTReader().read(latLng.toString());
		
		//Geometry geom = myWKBReader.read(myResultSet.getBytes("st_asbinary"));
		this.latLng = latLng;
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
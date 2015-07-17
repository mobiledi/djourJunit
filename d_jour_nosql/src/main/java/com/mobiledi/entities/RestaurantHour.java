package com.mobiledi.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.sql.Timestamp;


/**
 * The persistent class for the restaurant_hours database table.
 * 
 */
@Entity
@Table(name="restaurant_hours")
@NamedQueries({
	@NamedQuery(name="RestaurantHour.findAll", query="SELECT r FROM RestaurantHour r"),
	@NamedQuery(name="RestaurantHour.findAllWithId", query="SELECT r FROM RestaurantHour r WHERE r.restaurantMaster.id=:id")
	})
@JsonIgnoreProperties(ignoreUnknown=true)
public class RestaurantHour implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="active_flag")
	private Integer activeFlag;

	@Column(name="create_datetime")
	private Timestamp createDatetime;

	@Column(name="weekday_closing_hour")
	private Integer weekdayClosingHour;

	@Column(name="weekday_closing_minutes")
	private Integer weekdayClosingMinutes;

	@Column(name="weekday_opening_hour")
	private Integer weekdayOpeningHour;

	@Column(name="weekday_opening_minutes")
	private Integer weekdayOpeningMinutes;

	@Column(name="weekend_closing_hour")
	private Integer weekendClosingHour;

	@Column(name="weekend_closing_minutes")
	private Integer weekendClosingMinutes;

	@Column(name="weekend_opening_hour")
	private Integer weekendOpeningHour;

	@Column(name="weekend_opening_minutes")
	private Integer weekendOpeningMinutes;

	//bi-directional many-to-one association to RestaurantMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fk_restaurant_master_id")
	private RestaurantMaster restaurantMaster;

	public RestaurantHour() {
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

	public Timestamp getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Timestamp createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Integer getWeekdayClosingHour() {
		return this.weekdayClosingHour;
	}

	public void setWeekdayClosingHour(Integer weekdayClosingHour) {
		this.weekdayClosingHour = weekdayClosingHour;
	}

	public Integer getWeekdayClosingMinutes() {
		return this.weekdayClosingMinutes;
	}

	public void setWeekdayClosingMinutes(Integer weekdayClosingMinutes) {
		this.weekdayClosingMinutes = weekdayClosingMinutes;
	}

	public Integer getWeekdayOpeningHour() {
		return this.weekdayOpeningHour;
	}

	public void setWeekdayOpeningHour(Integer weekdayOpeningHour) {
		this.weekdayOpeningHour = weekdayOpeningHour;
	}

	public Integer getWeekdayOpeningMinutes() {
		return this.weekdayOpeningMinutes;
	}

	public void setWeekdayOpeningMinutes(Integer weekdayOpeningMinutes) {
		this.weekdayOpeningMinutes = weekdayOpeningMinutes;
	}

	public Integer getWeekendClosingHour() {
		return this.weekendClosingHour;
	}

	public void setWeekendClosingHour(Integer weekendClosingHour) {
		this.weekendClosingHour = weekendClosingHour;
	}

	public Integer getWeekendClosingMinutes() {
		return this.weekendClosingMinutes;
	}

	public void setWeekendClosingMinutes(Integer weekendClosingMinutes) {
		this.weekendClosingMinutes = weekendClosingMinutes;
	}

	public Integer getWeekendOpeningHour() {
		return this.weekendOpeningHour;
	}

	public void setWeekendOpeningHour(Integer weekendOpeningHour) {
		this.weekendOpeningHour = weekendOpeningHour;
	}

	public Integer getWeekendOpeningMinutes() {
		return this.weekendOpeningMinutes;
	}

	public void setWeekendOpeningMinutes(Integer weekendOpeningMinutes) {
		this.weekendOpeningMinutes = weekendOpeningMinutes;
	}

	public RestaurantMaster getRestaurantMaster() {
		return this.restaurantMaster;
	}

	public void setRestaurantMaster(RestaurantMaster restaurantMaster) {
		this.restaurantMaster = restaurantMaster;
	}

}
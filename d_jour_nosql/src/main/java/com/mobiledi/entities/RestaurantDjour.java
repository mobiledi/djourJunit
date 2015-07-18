package com.mobiledi.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the restaurant_djour database table.
 * 
 */
@Entity
@Table(name="restaurant_djour")
@NamedQuery(name="RestaurantDjour.findAll", query="SELECT r FROM RestaurantDjour r")
public class RestaurantDjour implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="active_status")
	private Boolean activeStatus;

	@Column(name="create_date")
	private Timestamp createDate;

	private String description;

	@Column(name="end_on")
	private Timestamp endOn;

	private float price;

	private Boolean repeat;

	@Column(name="special_name")
	private String specialName;

	@Column(name="special_type")
	private Integer specialType;

	@Column(name="start_from")
	private Timestamp startFrom;

	//bi-directional many-to-one association to DjourRepeat
	@OneToMany(mappedBy="restaurantDjour")
	private List<DjourRepeat> djourRepeats;

	//bi-directional many-to-one association to RestaurantMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fk_restauran_id")
	private RestaurantMaster restaurantMaster;

	public RestaurantDjour() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getEndOn() {
		return this.endOn;
	}

	public void setEndOn(Timestamp endOn) {
		this.endOn = endOn;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Boolean getRepeat() {
		return this.repeat;
	}

	public void setRepeat(Boolean repeat) {
		this.repeat = repeat;
	}

	public String getSpecialName() {
		return this.specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public Integer getSpecialType() {
		return this.specialType;
	}

	public void setSpecialType(Integer specialType) {
		this.specialType = specialType;
	}

	public Timestamp getStartFrom() {
		return this.startFrom;
	}

	public void setStartFrom(Timestamp startFrom) {
		this.startFrom = startFrom;
	}

	public List<DjourRepeat> getDjourRepeats() {
		return this.djourRepeats;
	}

	public void setDjourRepeats(List<DjourRepeat> djourRepeats) {
		this.djourRepeats = djourRepeats;
	}

	public DjourRepeat addDjourRepeat(DjourRepeat djourRepeat) {
		getDjourRepeats().add(djourRepeat);
		djourRepeat.setRestaurantDjour(this);

		return djourRepeat;
	}

	public DjourRepeat removeDjourRepeat(DjourRepeat djourRepeat) {
		getDjourRepeats().remove(djourRepeat);
		djourRepeat.setRestaurantDjour(null);

		return djourRepeat;
	}

	public RestaurantMaster getRestaurantMaster() {
		return this.restaurantMaster;
	}

	public void setRestaurantMaster(RestaurantMaster restaurantMaster) {
		this.restaurantMaster = restaurantMaster;
	}

}
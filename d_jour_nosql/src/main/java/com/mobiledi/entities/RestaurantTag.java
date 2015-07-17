package com.mobiledi.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonIgnoreType;


/**
 * The persistent class for the restaurant_tags database table.
 * 
 */
@Entity
@Table(name="restaurant_tags")
@NamedQueries({
	@NamedQuery(name="RestaurantTag.findAll", query="SELECT r FROM RestaurantTag r"),
	@NamedQuery(name="RestaurantTag.findAllWithId", query="SELECT r FROM RestaurantTag r WHERE r.restaurantMaster.id=:id")
	})
@JsonIgnoreProperties(ignoreUnknown=true)
public class RestaurantTag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="fk_profile_tags_id")
	private Integer fkProfileTagsId;

	//bi-directional many-to-one association to RestaurantMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fk_restaurant_id")
	private RestaurantMaster restaurantMaster;

	public RestaurantTag() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFkProfileTagsId() {
		return this.fkProfileTagsId;
	}

	public void setFkProfileTagsId(Integer fkProfileTagsId) {
		this.fkProfileTagsId = fkProfileTagsId;
	}

	public RestaurantMaster getRestaurantMaster() {
		return this.restaurantMaster;
	}

	public void setRestaurantMaster(RestaurantMaster restaurantMaster) {
		this.restaurantMaster = restaurantMaster;
	}

}
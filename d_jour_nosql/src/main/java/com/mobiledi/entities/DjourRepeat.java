package com.mobiledi.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the djour_repeat database table.
 * 
 */
@Entity
@Table(name="djour_repeat")
@NamedQuery(name="DjourRepeat.findAll", query="SELECT d FROM DjourRepeat d")
public class DjourRepeat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="repeat_on")
	private Integer repeatOn;

	//bi-directional many-to-one association to RestaurantDjour
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fk_djour_id")
	private RestaurantDjour restaurantDjour;

	public DjourRepeat() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRepeatOn() {
		return this.repeatOn;
	}

	public void setRepeatOn(Integer repeatOn) {
		this.repeatOn = repeatOn;
	}

	public RestaurantDjour getRestaurantDjour() {
		return this.restaurantDjour;
	}

	public void setRestaurantDjour(RestaurantDjour restaurantDjour) {
		this.restaurantDjour = restaurantDjour;
	}

}
package com.hibernate.demo.entity.tpsc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Builder;

@Builder
@Entity
@Table(name = "exterior")
@PrimaryKeyJoinColumn(name = "fk_vehicle_id")
public class Exterior extends Vehicle {

	@Column(name = "rating")
	private String rating;
	@Column(name = "model")
	private String model;

}

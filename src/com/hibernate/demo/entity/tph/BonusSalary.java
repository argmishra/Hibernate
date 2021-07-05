package com.hibernate.demo.entity.tph;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Builder;

@Builder
@Entity
@DiscriminatorValue("bonus_salary")
public class BonusSalary extends TotalSalary {

	@Column(name = "tax")
	private String tax;
	@Column(name = "travel")
	private Boolean travel;
}

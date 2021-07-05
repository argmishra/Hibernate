package com.hibernate.demo.entity.tph;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Builder;

@Builder
@Entity
@DiscriminatorValue("basic_salary")
public class BasicSalary extends TotalSalary {

	@Column(name = "amount")
	private Long amount;
	@Column(name = "medical")
	private Boolean medical;

}

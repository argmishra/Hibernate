package com.hibernate.demo.entity.mappings;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "numbers", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
@Data
@Builder
public class Number {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer numberId;

	@Column(name = "type", unique = true, nullable = false)
	private String type;

	@ManyToOne
	private Office office;
}

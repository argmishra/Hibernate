package com.hibernate.demo.entity.mappings;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "offices", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
@Data
@Builder
public class Office {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "office_id")
	private Set<Number> numbers;
}

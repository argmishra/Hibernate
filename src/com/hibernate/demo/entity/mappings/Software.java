package com.hibernate.demo.entity.mappings;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "software")
@Data
@Builder
public class Software {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "ram")
	private String ram;

	@Column(name = "version")
	private Integer version;

	@OneToOne(mappedBy = "software")
	private Mobile mobile;
}

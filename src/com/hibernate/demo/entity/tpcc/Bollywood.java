package com.hibernate.demo.entity.tpcc;

import java.time.Instant;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;

@Builder
@Entity
@Table(name = "bollywood")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")),
		@AttributeOverride(name = "name", column = @Column(name = "name")) })
public class Bollywood extends Movie {

	@Column(name = "date")
	private Instant date;
	@Column(name = "hero")
	private String hero;
}

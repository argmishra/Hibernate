package com.hibernate.demo.entity.mappings;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "subscriptions", uniqueConstraints = { @UniqueConstraint(columnNames = "ID") })
@Data
@Builder
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer subscriptionId;

	@Column(name = "sub_name")
	private String subscriptionName;

	@ManyToMany(mappedBy = "subscriptions")
	private Set<Reader> readers;
}

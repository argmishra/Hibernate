package com.hibernate.demo.entity.mappings;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.ToString;

@Entity
@Table(name = "readers", uniqueConstraints = { @UniqueConstraint(columnNames = "ID") })
@Builder
@ToString
public class Reader {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer readerId;

	@Column(name = "first_name")
	private String firstName;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "reader_subscriptions", joinColumns = {
			@JoinColumn(referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(referencedColumnName = "id") })
	private Set<Subscription> subscriptions;
}
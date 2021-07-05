package com.hibernate.demo.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Employee {

	private Long id;
	private String firstName;
	private String lastName;
}

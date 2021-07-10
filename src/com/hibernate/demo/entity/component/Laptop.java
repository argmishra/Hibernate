package com.hibernate.demo.entity.component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Laptop {

	private Long id;
	private String brand;
	private Hardware hardware;

}

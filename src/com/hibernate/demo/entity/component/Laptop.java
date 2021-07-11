package com.hibernate.demo.entity.component;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Laptop {

	private Long id;
	private String brand;
	private Hardware hardware;

}

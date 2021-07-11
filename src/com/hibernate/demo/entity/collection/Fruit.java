package com.hibernate.demo.entity.collection;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Fruit {

	private Long id;
	private String name;
	private List<String> locations;

}

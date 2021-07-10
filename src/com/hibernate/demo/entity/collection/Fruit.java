package com.hibernate.demo.entity.collection;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fruit {

	private Long id;
	private String name;
	private List<String> locations;

}

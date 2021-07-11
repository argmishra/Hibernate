package com.hibernate.demo.entity.collection;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Vegetable {

	private Long id;
	private String name;
	private Set<String> colours;
}

package com.hibernate.demo.entity.collection;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Password {

	private int id;
	private String website;
	private Map<String, String> hint;

}

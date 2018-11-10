package org.labcabrera.samples.mongo.ddd.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "products")
@Getter
@Setter
public class Product {

	@Id
	private String id;

	private String code;

	private String name;

}

package org.labcabrera.samples.mongo.ddd.app.api.resources;

import org.labcabrera.samples.mongo.ddd.app.model.Product;
import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;

@Getter
public class ProductResource extends ResourceSupport {

	private final Product product;

	public ProductResource(Product entity) {
		this.product = entity;
	}

}

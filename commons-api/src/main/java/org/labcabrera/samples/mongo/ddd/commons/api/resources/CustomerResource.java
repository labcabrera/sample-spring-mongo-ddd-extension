package org.labcabrera.samples.mongo.ddd.commons.api.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.labcabrera.samples.mongo.ddd.commons.api.controller.CustomerControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;

@Getter
@Relation(collectionRelation = "customers")
public class CustomerResource<E> extends ResourceSupport {

	private final Customer<E> customer;

	public CustomerResource(Customer<E> entity) {
		this.customer = entity;
		add(linkTo(methodOn(CustomerControllerDefinition.class).findById(entity.getId())).withSelfRel());
		add(linkTo(methodOn(CustomerControllerDefinition.class).findCustomerRelations(entity.getId(), null))
			.withRel("relations"));
	}

}

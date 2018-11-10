package org.labcabrera.samples.mongo.ddd.commons.api.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.labcabrera.samples.mongo.ddd.commons.api.controller.ContractControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.api.controller.ContractCustomerRelationControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.api.controller.CustomerControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.model.ContractCustomerRelation;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;

@Getter
@Relation(collectionRelation = "relations")
public class ContractCustomerRelationResource<E, T> extends ResourceSupport {

	private final ContractCustomerRelation<E, T> relation;

	public ContractCustomerRelationResource(ContractCustomerRelation<E, T> entity) {
		this.relation = entity;
		add(linkTo(methodOn(ContractCustomerRelationControllerDefinition.class).findById(entity.getId()))
			.withSelfRel());
		add(linkTo(methodOn(ContractControllerDefinition.class).findById(entity.getContract().getId()))
			.withRel("contract"));
		add(linkTo(methodOn(CustomerControllerDefinition.class).findById(entity.getCustomer().getId()))
			.withRel("customer"));
	}

}

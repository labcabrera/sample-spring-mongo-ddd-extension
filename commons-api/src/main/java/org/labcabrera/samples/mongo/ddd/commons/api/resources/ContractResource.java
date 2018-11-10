package org.labcabrera.samples.mongo.ddd.commons.api.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.labcabrera.samples.mongo.ddd.commons.api.controller.ContractControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;

@Getter
@Relation(collectionRelation = "contracts")
public class ContractResource<E> extends ResourceSupport {

	private final Contract<E> contract;

	public ContractResource(Contract<E> entity) {
		this.contract = entity;
		add(linkTo(methodOn(ContractControllerDefinition.class).findById(entity.getId())).withSelfRel());
		add(linkTo(methodOn(ContractControllerDefinition.class).findContractRelations(entity.getId(), null))
			.withRel("relations"));
	}

}

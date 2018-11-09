package org.labcabrera.samples.mongo.ddd.commons.api.resources;

import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;

@Getter
@Relation(collectionRelation = "contracts")
public class ContractResource extends ResourceSupport {

	private final Contract contract;

	public ContractResource(Contract entity) {
		this.contract = entity;
		//add(linkTo(methodOn(PolicyControllerDefinition.class).findById(id)).withSelfRel());
	}

}

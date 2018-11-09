package org.labcabrera.samples.mongo.ddd.commons.api.hateoas.resources;

import org.labcabrera.samples.mongo.ddd.commons.model.ContractCustomerRelation;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;

@Getter
@Relation(collectionRelation = "relations")
public class ContractCustomerRelationResource extends ResourceSupport {

	private final ContractCustomerRelation relation;

	public ContractCustomerRelationResource(ContractCustomerRelation entity) {
		this.relation = entity;
	}

}

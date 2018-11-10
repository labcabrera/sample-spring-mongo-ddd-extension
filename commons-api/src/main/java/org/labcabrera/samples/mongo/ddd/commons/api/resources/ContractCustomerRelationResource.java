package org.labcabrera.samples.mongo.ddd.commons.api.resources;

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
	}

}

package org.labcabrera.samples.mongo.ddd.commons.service;

import org.labcabrera.samples.mongo.ddd.commons.data.ContractRelationRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.ContractCustomerRelation;
import org.labcabrera.samples.mongo.ddd.commons.model.QContractCustomerRelation;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.StringPath;

@Service
public class ContractRelationService<E, T> extends AbstractSecurityService<ContractCustomerRelation<E, T>> {

	public ContractRelationService(ContractRelationRepository<E, T> repository) {
		super(repository);
	}

	@Override
	protected ListPath<String, StringPath> getAutorizationPaths() {
		return QContractCustomerRelation.contractCustomerRelation.authorization;
	}

}

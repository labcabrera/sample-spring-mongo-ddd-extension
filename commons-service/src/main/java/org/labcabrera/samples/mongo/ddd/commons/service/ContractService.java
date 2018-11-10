package org.labcabrera.samples.mongo.ddd.commons.service;

import org.labcabrera.samples.mongo.ddd.commons.data.ContractRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.labcabrera.samples.mongo.ddd.commons.model.QContract;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.StringPath;

/**
 * 
 * @param <E> Contract additional data type
 */
@Service
public class ContractService<E> extends AbstractSecurityService<Contract<E>> {

	public ContractService(ContractRepository<E> repository) {
		super(repository);
	}

	@Override
	protected ListPath<String, StringPath> getAutorizationPaths() {
		return QContract.contract.authorization;
	}

}

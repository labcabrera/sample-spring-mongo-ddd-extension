package org.labcabrera.samples.mongo.ddd.commons.service;

import org.labcabrera.samples.mongo.ddd.commons.data.ContractRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.labcabrera.samples.mongo.ddd.commons.model.QContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.StringPath;

@Service
public class ContractService extends AbstractSecurityService<Contract> {

	@Autowired
	private ContractRepository repository;

	@Override
	protected ListPath<String, StringPath> getAutorizationPaths() {
		return QContract.contract.authorization;
	}

	@Override
	protected PagingAndSortingRepository<Contract, String> getPagedRepository() {
		return repository;
	}

	@Override
	protected QuerydslPredicateExecutor<Contract> getQuerydslRepository() {
		return repository;
	}

}

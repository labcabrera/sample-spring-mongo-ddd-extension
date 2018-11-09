package org.labcabrera.samples.mongo.ddd.commons.data;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContractRepository
		extends PagingAndSortingRepository<Contract, String>, QuerydslPredicateExecutor<Contract> {

	Optional<Contract> findByContractNumber(String contractNumber);

}

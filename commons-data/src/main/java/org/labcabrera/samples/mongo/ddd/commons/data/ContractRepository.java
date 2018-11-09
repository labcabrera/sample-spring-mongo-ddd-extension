package org.labcabrera.samples.mongo.ddd.commons.data;

import java.util.Map;
import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.labcabrera.samples.mongo.ddd.commons.model.QContract;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.google.common.collect.ImmutableMap;
import com.querydsl.core.types.Path;

public interface ContractRepository
	extends PagingAndSortingRepository<Contract, String>, QuerydslPredicateExecutor<Contract> {

	@SuppressWarnings("rawtypes")
	Map<String, Path> PATH_MAP = ImmutableMap.<String, Path>builder() //@formatter:off
		.put("id", QContract.contract.id)
		.put("contractNumber", QContract.contract.contractNumber)
		.put("effective", QContract.contract.effective)
		.build(); //@formatter.on
	
	Optional<Contract> findByContractNumber(String contractNumber);

}

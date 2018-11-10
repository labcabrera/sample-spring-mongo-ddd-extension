package org.labcabrera.samples.mongo.ddd.commons.data;

import java.util.Map;
import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.labcabrera.samples.mongo.ddd.commons.model.QContract;

import com.google.common.collect.ImmutableMap;
import com.querydsl.core.types.Path;

public interface ContractRepository<E> extends ApiRepository<Contract<E>> {

	@SuppressWarnings("rawtypes")
	Map<String, Path> PATH_MAP = ImmutableMap.<String, Path>builder() //@formatter:off
		.put("id", QContract.contract.id)
		.put("contractNumber", QContract.contract.contractNumber)
		.put("effective", QContract.contract.effective)
		.build(); //@formatter.on
	
	Optional<Contract<E>> findByContractNumber(String contractNumber);

}

package org.labcabrera.samples.mongo.ddd.commons.data;

import java.util.Map;

import org.labcabrera.samples.mongo.ddd.commons.model.ContractCustomerRelation;
import org.labcabrera.samples.mongo.ddd.commons.model.QContractCustomerRelation;

import com.google.common.collect.ImmutableMap;
import com.querydsl.core.types.Path;

public interface ContractRelationRepository<E, T> extends ApiRepository<ContractCustomerRelation<E, T>> {

	@SuppressWarnings("rawtypes")
	Map<String, Path> PATH_MAP = ImmutableMap.<String, Path>builder() //@formatter:off
		.put("id", QContractCustomerRelation.contractCustomerRelation.id)
		.build(); //@formatter.on

}

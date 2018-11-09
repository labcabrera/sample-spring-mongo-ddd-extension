package org.labcabrera.samples.mongo.ddd.commons.data;

import java.util.Map;

import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.labcabrera.samples.mongo.ddd.commons.model.QCustomer;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.google.common.collect.ImmutableMap;
import com.querydsl.core.types.Path;

public interface CustomerRepository
	extends PagingAndSortingRepository<Customer, String>, QuerydslPredicateExecutor<Customer> {

	@SuppressWarnings("rawtypes")
	Map<String, Path> PATH_MAP = ImmutableMap.<String, Path>builder() //@formatter:off
		.put("id", QCustomer.customer.id)
		.put("name", QCustomer.customer.name)
		.put("surname", QCustomer.customer.surname)
		.build(); //@formatter.on

}

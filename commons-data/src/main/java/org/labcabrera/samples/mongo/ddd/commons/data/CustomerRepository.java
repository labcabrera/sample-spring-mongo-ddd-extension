package org.labcabrera.samples.mongo.ddd.commons.data;

import java.util.Map;

import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.labcabrera.samples.mongo.ddd.commons.model.QCustomer;

import com.google.common.collect.ImmutableMap;
import com.querydsl.core.types.Path;

public interface CustomerRepository<E> extends ApiRepository<Customer<E>> {

	@SuppressWarnings("rawtypes")
	Map<String, Path> PATH_MAP = ImmutableMap.<String, Path>builder() //@formatter:off
		.put("id", QCustomer.customer.id)
		.put("name", QCustomer.customer.name)
		.put("surname", QCustomer.customer.surname)
		.build(); //@formatter.on

}

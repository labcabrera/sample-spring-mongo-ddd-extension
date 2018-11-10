package org.labcabrera.samples.mongo.ddd.commons.service;

import org.labcabrera.samples.mongo.ddd.commons.data.CustomerRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.labcabrera.samples.mongo.ddd.commons.model.QCustomer;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.StringPath;

@Service
public class CustomerService<E> extends AbstractSecurityService<Customer<E>> {

	public CustomerService(CustomerRepository<E> repository) {
		super(repository);
	}

	@Override
	protected ListPath<String, StringPath> getAutorizationPaths() {
		return QCustomer.customer.authorization;
	}

}

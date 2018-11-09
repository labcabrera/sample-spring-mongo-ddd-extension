package org.labcabrera.samples.mongo.ddd.commons.service;

import org.labcabrera.samples.mongo.ddd.commons.data.CustomerRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.labcabrera.samples.mongo.ddd.commons.model.QCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.StringPath;

@Service
public class CustomerService extends AbstractSecurityService<Customer> {

	@Autowired
	private CustomerRepository repository;

	@Override
	protected ListPath<String, StringPath> getAutorizationPaths() {
		return QCustomer.customer.authorization;
	}

	@Override
	protected PagingAndSortingRepository<Customer, String> getPagedRepository() {
		return repository;
	}

	@Override
	protected QuerydslPredicateExecutor<Customer> getQuerydslRepository() {
		return repository;
	}

}

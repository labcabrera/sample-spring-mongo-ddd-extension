package org.labcabrera.samples.mongo.ddd.commons.api.controller.impl;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.commons.api.controller.CustomerControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.api.errors.EntityNotFoundException;
import org.labcabrera.samples.mongo.ddd.commons.api.hateoas.assemblers.CustomerAssembler;
import org.labcabrera.samples.mongo.ddd.commons.api.hateoas.resources.ContractCustomerRelationResource;
import org.labcabrera.samples.mongo.ddd.commons.api.hateoas.resources.CustomerResource;
import org.labcabrera.samples.mongo.ddd.commons.api.querydsl.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.data.ContractRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.labcabrera.samples.mongo.ddd.commons.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.querydsl.core.types.Predicate;

import springfox.documentation.annotations.ApiIgnore;

public class CustomerController implements CustomerControllerDefinition {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PagedResourcesAssembler<Customer> customerPagedAssembler;

	@Autowired
	private CustomerAssembler customerAssembler;

	@Autowired
	private PredicateParser predicateParser;

	@Override
	public ResponseEntity<CustomerResource> findById(@PathVariable String id) {
		return customerService.findById(id).map(p -> ResponseEntity.ok(new CustomerResource(p)))
			.orElseThrow(() -> new EntityNotFoundException("Missing customer " + id));
	}

	@Override
	public ResponseEntity<PagedResources<CustomerResource>> find(
		@RequestParam(name = "search", required = false, defaultValue = "") String search,
		@ApiIgnore Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, ContractRepository.PATH_MAP);
		Page<Customer> page = predicate.isPresent() ? customerService.findAll(predicate.get(), pageable)
			: customerService.findAll(pageable);
		return ResponseEntity.ok(customerPagedAssembler.toResource(page, customerAssembler));
	}

	@Override
	public ResponseEntity<PagedResources<ContractCustomerRelationResource>> findCustomerRelations(Long id,
		Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}

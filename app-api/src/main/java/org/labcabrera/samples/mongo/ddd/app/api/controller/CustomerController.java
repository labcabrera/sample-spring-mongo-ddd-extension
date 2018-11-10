package org.labcabrera.samples.mongo.ddd.app.api.controller;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.app.model.CustomerAdditionalData;
import org.labcabrera.samples.mongo.ddd.commons.api.controller.CustomerControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.api.errors.EntityNotFoundException;
import org.labcabrera.samples.mongo.ddd.commons.api.querydsl.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.api.resources.ContractCustomerRelationResource;
import org.labcabrera.samples.mongo.ddd.commons.api.resources.CustomerResource;
import org.labcabrera.samples.mongo.ddd.commons.data.CustomerRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.labcabrera.samples.mongo.ddd.commons.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/v1/customers", produces = "application/hal+json")
@Api(tags = "Customers")
public class CustomerController implements
	CustomerControllerDefinition<CustomerResource<CustomerAdditionalData>, ContractCustomerRelationResource> {

	private final CustomerService<CustomerAdditionalData> service;
	private final PagedResourcesAssembler<Customer<CustomerAdditionalData>> pagedAssembler;
	private final ResourceAssembler<Customer<CustomerAdditionalData>, CustomerResource<CustomerAdditionalData>> assembler;
	private final PredicateParser predicateParser;

	public CustomerController(CustomerService<CustomerAdditionalData> service,
		PagedResourcesAssembler<Customer<CustomerAdditionalData>> pagedAssembler, PredicateParser predicateParser) {
		this.service = service;
		this.pagedAssembler = pagedAssembler;
		this.predicateParser = predicateParser;
		assembler = new ResourceAssembler<Customer<CustomerAdditionalData>, CustomerResource<CustomerAdditionalData>>() {

			@Override
			public CustomerResource<CustomerAdditionalData> toResource(Customer<CustomerAdditionalData> entity) {
				return new CustomerResource<>(entity);
			}
		};
	}

	@Override
	public ResponseEntity<CustomerResource<CustomerAdditionalData>> findById(@PathVariable String id) {
		return service.findById(id).map(p -> ResponseEntity.ok(assembler.toResource(p)))
			.orElseThrow(() -> new EntityNotFoundException("Missing entity " + id));
	}

	@Override
	public ResponseEntity<PagedResources<CustomerResource<CustomerAdditionalData>>> find(
		@RequestParam(name = "search", required = false, defaultValue = "") String search,
		@ApiIgnore @PageableDefault(sort = { "name", "surname" }) Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, CustomerRepository.PATH_MAP);
		Page<Customer<CustomerAdditionalData>> page = predicate.isPresent() ? service.findAll(predicate.get(), pageable)
			: service.findAll(pageable);
		return ResponseEntity.ok(pagedAssembler.toResource(page, assembler));
	}

	@Override
	public ResponseEntity<PagedResources<ContractCustomerRelationResource>> findCustomerRelations(String id,
		Pageable pageable) {
		return null;
	}

}

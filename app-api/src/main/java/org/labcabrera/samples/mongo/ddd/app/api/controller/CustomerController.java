package org.labcabrera.samples.mongo.ddd.app.api.controller;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.app.model.ContractAD;
import org.labcabrera.samples.mongo.ddd.app.model.CustomerAD;
import org.labcabrera.samples.mongo.ddd.commons.api.controller.CustomerControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.api.errors.EntityNotFoundException;
import org.labcabrera.samples.mongo.ddd.commons.api.querydsl.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.api.resources.ContractCustomerRelationResource;
import org.labcabrera.samples.mongo.ddd.commons.api.resources.CustomerResource;
import org.labcabrera.samples.mongo.ddd.commons.data.CustomerRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.ContractCustomerRelation;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.labcabrera.samples.mongo.ddd.commons.model.QContractCustomerRelation;
import org.labcabrera.samples.mongo.ddd.commons.service.ContractRelationService;
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
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/v1/customers", produces = "application/hal+json")
@Api(tags = "Customers")
@AllArgsConstructor
public class CustomerController
		implements CustomerControllerDefinition<CustomerResource<CustomerAD>, ContractCustomerRelationResource<ContractAD, CustomerAD>> {

	private final CustomerService<CustomerAD> customerService;
	private final ContractRelationService<ContractAD, CustomerAD> relationService;
	private final PagedResourcesAssembler<Customer<CustomerAD>> customerPagedAssembler;
	private final PagedResourcesAssembler<ContractCustomerRelation<ContractAD, CustomerAD>> relationPagedAssembler;
	private final PredicateParser predicateParser;

	private final ResourceAssembler<Customer<CustomerAD>, CustomerResource<CustomerAD>> customerAssembler = (e) -> new CustomerResource<>(
			e);
	private final ResourceAssembler<ContractCustomerRelation<ContractAD, CustomerAD>, ContractCustomerRelationResource<ContractAD, CustomerAD>> relationAssembler = (
			e) -> new ContractCustomerRelationResource<>(e);

	@Override
	public ResponseEntity<CustomerResource<CustomerAD>> findById(@PathVariable String id) {
		return customerService.findById(id).map(p -> ResponseEntity.ok(customerAssembler.toResource(p)))
				.orElseThrow(() -> new EntityNotFoundException("Missing entity " + id));
	}

	@Override
	public ResponseEntity<PagedResources<CustomerResource<CustomerAD>>> find(
			@RequestParam(name = "search", required = false, defaultValue = "") String search,
			@ApiIgnore @PageableDefault(sort = { "name", "surname" }) Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, CustomerRepository.PATH_MAP);
		Page<Customer<CustomerAD>> page = predicate.isPresent() ? customerService.findAll(predicate.get(), pageable)
				: customerService.findAll(pageable);
		return ResponseEntity.ok(customerPagedAssembler.toResource(page, customerAssembler));
	}

	@Override
	public ResponseEntity<PagedResources<ContractCustomerRelationResource<ContractAD, CustomerAD>>> findCustomerRelations(String id,
			Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Predicate predicate = QContractCustomerRelation.contractCustomerRelation.customer.id.eq(id);
		Page<ContractCustomerRelation<ContractAD, CustomerAD>> page = relationService.findAll(predicate, pageable);
		return ResponseEntity.ok(relationPagedAssembler.toResource(page, relationAssembler));
	}

}

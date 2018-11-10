package org.labcabrera.samples.mongo.ddd.commons.api.controller.impl;

import java.util.Map;

import org.labcabrera.samples.mongo.ddd.commons.api.controller.CustomerControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.api.hateoas.resources.ContractCustomerRelationResource;
import org.labcabrera.samples.mongo.ddd.commons.api.hateoas.resources.CustomerResource;
import org.labcabrera.samples.mongo.ddd.commons.api.querydsl.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.data.CustomerRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.labcabrera.samples.mongo.ddd.commons.service.AbstractSecurityService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.querydsl.core.types.Path;

import springfox.documentation.annotations.ApiIgnore;

public abstract class CustomerController<E> extends AbstractResourceController<Customer<E>, CustomerResource>
	implements CustomerControllerDefinition<E> {

	public CustomerController(AbstractSecurityService<Customer<E>> service,
		PagedResourcesAssembler<Customer<E>> pagedAssembler,
		ResourceAssemblerSupport<Customer<E>, CustomerResource> assembler, PredicateParser predicateParser) {
		super(service, pagedAssembler, assembler, predicateParser);
	}

	// NOTA definimos este metodo porque si no no lo detecta springfox
	@Override
	public ResponseEntity<CustomerResource> findById(@PathVariable String id) {
		return super.findById(id);
	}

	// NOTA definimos este metodo porque si no no lo detecta springfox
	@Override
	public ResponseEntity<PagedResources<CustomerResource>> find(
		@RequestParam(name = "search", required = false, defaultValue = "") String search,
		@ApiIgnore @PageableDefault(sort = "surname,name") Pageable pageable) {
		return super.find(search, pageable);
	}

	@Override
	public ResponseEntity<PagedResources<ContractCustomerRelationResource>> findCustomerRelations(Long id,
		Pageable pageable) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Map<String, Path> getEntityPaths() {
		return CustomerRepository.PATH_MAP;
	}

}

package org.labcabrera.samples.mongo.ddd.commons.api.controller.impl;

import java.util.Map;

import org.labcabrera.samples.mongo.ddd.commons.api.controller.CustomerControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.api.hateoas.resources.ContractCustomerRelationResource;
import org.labcabrera.samples.mongo.ddd.commons.api.hateoas.resources.CustomerResource;
import org.labcabrera.samples.mongo.ddd.commons.data.ContractRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.querydsl.core.types.Path;

import springfox.documentation.annotations.ApiIgnore;

public class CustomerController extends AbstractResourceController<Customer, CustomerResource>
	implements CustomerControllerDefinition {

	@Override
	public ResponseEntity<CustomerResource> findById(@PathVariable String id) {
		return super.findById(id);
	}

	@Override
	public ResponseEntity<PagedResources<CustomerResource>> find(
		@RequestParam(name = "search", required = false, defaultValue = "") String search,
		@ApiIgnore @PageableDefault(sort = "surname,name") Pageable pageable) {
		return super.find(search, pageable);
	}

	@Override
	public ResponseEntity<PagedResources<ContractCustomerRelationResource>> findCustomerRelations(Long id,
		Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String, Path> getEntityPaths() {
		return ContractRepository.PATH_MAP;
	}

}

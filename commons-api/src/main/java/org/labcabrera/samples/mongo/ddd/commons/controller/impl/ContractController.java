package org.labcabrera.samples.mongo.ddd.commons.controller.impl;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.commons.api.errors.EntityNotFoundException;
import org.labcabrera.samples.mongo.ddd.commons.api.predicate.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.api.resources.ContractAssembler;
import org.labcabrera.samples.mongo.ddd.commons.api.resources.ContractResource;
import org.labcabrera.samples.mongo.ddd.commons.controller.ContractControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.data.ContractRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.labcabrera.samples.mongo.ddd.commons.service.ContractService;
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

public class ContractController implements ContractControllerDefinition {

	@Autowired
	private ContractService contractService;

	@Autowired
	private PagedResourcesAssembler<Contract> contractPagedAssembler;

	@Autowired
	private ContractAssembler contractAssembler;

	@Autowired
	private PredicateParser predicateParser;

	@Override
	public ResponseEntity<ContractResource> findById(@PathVariable String id) {
		return contractService.findById(id).map(p -> ResponseEntity.ok(new ContractResource(p)))
			.orElseThrow(() -> new EntityNotFoundException("Missing contract " + id));
	}

	@Override
	public ResponseEntity<PagedResources<ContractResource>> find(@RequestParam String search,
		@ApiIgnore Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, ContractRepository.PATH_MAP);
		Page<Contract> page = predicate.isPresent() ? contractService.findAll(predicate.get(), pageable)
			: contractService.findAll(pageable);
		return ResponseEntity.ok(contractPagedAssembler.toResource(page, contractAssembler));
	}

	@Override
	public ResponseEntity<PagedResources<ContractResource>> findContractRelations(@PathVariable String policyId,
		@ApiIgnore Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}

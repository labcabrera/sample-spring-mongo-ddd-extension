package org.labcabrera.samples.mongo.ddd.app.api.controller;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.app.model.ContractAD;
import org.labcabrera.samples.mongo.ddd.app.model.CustomerAD;
import org.labcabrera.samples.mongo.ddd.commons.api.SwaggerConfig;
import org.labcabrera.samples.mongo.ddd.commons.api.controller.ContractControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.api.errors.EntityNotFoundException;
import org.labcabrera.samples.mongo.ddd.commons.api.querydsl.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.api.resources.ContractCustomerRelationResource;
import org.labcabrera.samples.mongo.ddd.commons.api.resources.ContractResource;
import org.labcabrera.samples.mongo.ddd.commons.data.ContractRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.labcabrera.samples.mongo.ddd.commons.model.ContractCustomerRelation;
import org.labcabrera.samples.mongo.ddd.commons.model.QContractCustomerRelation;
import org.labcabrera.samples.mongo.ddd.commons.service.ContractRelationService;
import org.labcabrera.samples.mongo.ddd.commons.service.ContractService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor
public class ContractController
		implements ContractControllerDefinition<ContractResource<ContractAD>, ContractCustomerRelationResource<ContractAD, CustomerAD>> {

	private final ContractService<ContractAD> contractService;
	private final ContractRelationService<ContractAD, CustomerAD> relationService;
	private final PagedResourcesAssembler<Contract<ContractAD>> contractPagedAssembler;
	private final PagedResourcesAssembler<ContractCustomerRelation<ContractAD, CustomerAD>> relationPagedAssembler;
	private final PredicateParser predicateParser;

	private final ResourceAssembler<Contract<ContractAD>, ContractResource<ContractAD>> contractAssembler = (e) -> new ContractResource<>(
			e);
	private final ResourceAssembler<ContractCustomerRelation<ContractAD, CustomerAD>, ContractCustomerRelationResource<ContractAD, CustomerAD>> relationAssembler = (
			e) -> new ContractCustomerRelationResource<>(e);

	@Override
	public ResponseEntity<ContractResource<ContractAD>> findById(@PathVariable("id") String id) {
		return contractService.findById(id).map(e -> ResponseEntity.ok(new ContractResource<>(e)))
				.orElseThrow(() -> new EntityNotFoundException("Missing entity " + id));
	}

	@Override
	public ResponseEntity<PagedResources<ContractResource<ContractAD>>> find(
			@RequestParam(name = "search", required = false, defaultValue = "") String search,
			@ApiIgnore @PageableDefault(sort = "surname,name") Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, ContractRepository.PATH_MAP);
		Page<Contract<ContractAD>> page = predicate.isPresent() ? contractService.findAll(predicate.get(), pageable)
				: contractService.findAll(pageable);
		return ResponseEntity.ok(contractPagedAssembler.toResource(page, contractAssembler));
	}

	@Override
	public ResponseEntity<PagedResources<ContractCustomerRelationResource<ContractAD, CustomerAD>>> findContractRelations(
			@PathVariable("id") String id, @ApiIgnore Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Predicate predicate = QContractCustomerRelation.contractCustomerRelation.contract.id.eq(id);
		Page<ContractCustomerRelation<ContractAD, CustomerAD>> page = relationService.findAll(predicate, pageable);
		return ResponseEntity.ok(relationPagedAssembler.toResource(page, relationAssembler));
	}

	@PostMapping
	@ApiOperation(value = "Contract creation", authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	public ResponseEntity<PagedResources<ContractResource<ContractAD>>> create(@RequestBody Contract<ContractAD> entity) {

		return null;
	}

}

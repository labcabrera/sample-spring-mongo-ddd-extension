package org.labcabrera.samples.mongo.ddd.app.api.controller;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.app.model.ContractAdditionalData;
import org.labcabrera.samples.mongo.ddd.commons.api.SwaggerConfig;
import org.labcabrera.samples.mongo.ddd.commons.api.controller.ContractControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.api.errors.EntityNotFoundException;
import org.labcabrera.samples.mongo.ddd.commons.api.querydsl.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.api.resources.ContractResource;
import org.labcabrera.samples.mongo.ddd.commons.data.ContractRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
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
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class ContractController implements
	ContractControllerDefinition<ContractResource<ContractAdditionalData>, ContractResource<ContractAdditionalData>> {

	private final ContractService<ContractAdditionalData> service;
	private final PagedResourcesAssembler<Contract<ContractAdditionalData>> pagedAssembler;
	private final ResourceAssembler<Contract<ContractAdditionalData>, ContractResource<ContractAdditionalData>> assembler;
	private final PredicateParser predicateParser;

	public ContractController(ContractService<ContractAdditionalData> service,
		PagedResourcesAssembler<Contract<ContractAdditionalData>> pagedAssembler, PredicateParser predicateParser) {
		this.service = service;
		this.pagedAssembler = pagedAssembler;
		this.predicateParser = predicateParser;
		this.assembler = new ResourceAssembler<Contract<ContractAdditionalData>, ContractResource<ContractAdditionalData>>() {

			@Override
			public ContractResource<ContractAdditionalData> toResource(Contract<ContractAdditionalData> entity) {
				return new ContractResource<>(entity);
			}
		};
	}

	@Override
	public ResponseEntity<ContractResource<ContractAdditionalData>> findById(@PathVariable("id") String id) {
		return service.findById(id).map(e -> ResponseEntity.ok(new ContractResource<>(e)))
			.orElseThrow(() -> new EntityNotFoundException("Missing entity " + id));
	}

	@Override
	public ResponseEntity<PagedResources<ContractResource<ContractAdditionalData>>> find(
		@RequestParam(name = "search", required = false, defaultValue = "") String search,
		@ApiIgnore @PageableDefault(sort = "surname,name") Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, ContractRepository.PATH_MAP);
		Page<Contract<ContractAdditionalData>> page = predicate.isPresent() ? service.findAll(predicate.get(), pageable)
			: service.findAll(pageable);
		return ResponseEntity.ok(pagedAssembler.toResource(page, assembler));
	}

	@Override
	public ResponseEntity<PagedResources<ContractResource<ContractAdditionalData>>> findContractRelations(
		@PathVariable("id") String id, @ApiIgnore Pageable pageable) {
		return null;
	}

	@PostMapping
	@ApiOperation(value = "Contract creation", authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	public ResponseEntity<PagedResources<ContractResource<ContractAdditionalData>>> create(
		@RequestBody Contract<ContractAdditionalData> entity) {
		return null;
	}

}

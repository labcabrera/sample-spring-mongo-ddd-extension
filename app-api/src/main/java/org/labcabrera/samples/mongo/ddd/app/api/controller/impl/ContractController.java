package org.labcabrera.samples.mongo.ddd.app.api.controller.impl;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.app.model.ContractAdditionalData;
import org.labcabrera.samples.mongo.ddd.commons.api.SwaggerConfig;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/v1/contracts", produces = "application/hal+json")
@Api(tags = "Contracts")
public class ContractController {

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

	@GetMapping("/{id}")
	@ApiOperation(value = "Contract search by id",
		authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	public ResponseEntity<ContractResource<ContractAdditionalData>> findById(@PathVariable("id") String id) {
		return service.findById(id).map(e -> ResponseEntity.ok(new ContractResource<>(e)))
			.orElseThrow(() -> new EntityNotFoundException("Missing entity " + id));
	}

	//@formatter:off
	@GetMapping
	@ApiOperation(value = "Contract search", authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "Page number", required = false, dataType = "string", paramType = "query", defaultValue = "0"),
		@ApiImplicitParam(name = "size", value = "Page size", required = false, dataType = "string", paramType = "query", defaultValue = "10"),
		@ApiImplicitParam(name = "sort", value = "Sort expression", required = false, dataType = "string", paramType = "query", example = "name,asc") })
	//@formatter:on
	public ResponseEntity<PagedResources<ContractResource<ContractAdditionalData>>> find(
		@RequestParam(name = "search", required = false, defaultValue = "") String search,
		@ApiIgnore @PageableDefault(sort = "surname,name") Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, ContractRepository.PATH_MAP);
		Page<Contract<ContractAdditionalData>> page = predicate.isPresent() ? service.findAll(predicate.get(), pageable)
			: service.findAll(pageable);
		return ResponseEntity.ok(pagedAssembler.toResource(page, assembler));
	}

	@PostMapping
	@ApiOperation(value = "Contract creation", authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	public ResponseEntity<PagedResources<ContractResource<ContractAdditionalData>>> create(
		@RequestBody Contract<ContractAdditionalData> entity) {
		return null;
	}

}

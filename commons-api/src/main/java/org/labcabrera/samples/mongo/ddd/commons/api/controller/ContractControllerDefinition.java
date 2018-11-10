package org.labcabrera.samples.mongo.ddd.commons.api.controller;

import org.labcabrera.samples.mongo.ddd.commons.api.SwaggerConfig;
import org.labcabrera.samples.mongo.ddd.commons.api.resources.ContractResource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/v1/contracts", produces = "application/hal+json")
@Api(tags = "Contracts")
public interface ContractControllerDefinition<E> { //@formatter:off

	@GetMapping("/{id}")
	@ApiOperation(
		value = "Contract search by id",
		authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	ResponseEntity<ContractResource<E>> findById(
		@ApiParam(name = "id", value = "Policy identifier", required = true)
		@PathVariable(value = "id", required = true)
		String id);

	@GetMapping
	@ApiOperation(
		value = "Contract search",
		authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "Page number", required = false, dataType = "string", paramType = "query", defaultValue = "0"),
		@ApiImplicitParam(name = "size", value = "Page size", required = false, dataType = "string", paramType = "query", defaultValue = "10"),
		@ApiImplicitParam(name = "sort", value = "Sort expression", required = false, dataType = "string", paramType = "query", example = "name,asc") })
	ResponseEntity<PagedResources<ContractResource<E>>> find(
		@RequestParam(value = "search", required = false, defaultValue = "") String search,
		@ApiIgnore Pageable pageable);

	@GetMapping("/{id}/relations")
	@ApiOperation(
		value = "Search person relations from a given contract",
		authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "Page number", required = false, dataType = "string", paramType = "query", defaultValue = "0"),
		@ApiImplicitParam(name = "size", value = "Page size", required = false, dataType = "string", paramType = "query", defaultValue = "10"),
		@ApiImplicitParam(name = "sort", value = "Sort expression", required = false, dataType = "string", paramType = "query", example = "name,asc") })
	ResponseEntity<PagedResources<ContractResource<E>>> findContractRelations(
		@ApiParam(value = "Policy identifier", required = true)
		@PathVariable(value = "id", required = true)
		String policyId,
		
		@ApiIgnore
		Pageable pageable);

} //@formatter:on

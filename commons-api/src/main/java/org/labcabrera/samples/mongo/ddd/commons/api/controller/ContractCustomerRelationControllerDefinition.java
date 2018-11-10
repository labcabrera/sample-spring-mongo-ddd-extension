package org.labcabrera.samples.mongo.ddd.commons.api.controller;

import org.labcabrera.samples.mongo.ddd.commons.api.SwaggerConfig;
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
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/v1/relations", produces = "application/hal+json")
@Api(tags = "Contract customer relations")
public interface ContractCustomerRelationControllerDefinition<E> { //@formatter:off

	@GetMapping("/{id}")
	@ApiOperation(
		value = "Relation search by id",
		authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	ResponseEntity<E> findById(
		@PathVariable("id")
		String id);

	@GetMapping
	@ApiOperation(
		value = "Relation search",
		authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "Page number", required = false, dataType = "string", paramType = "query", defaultValue = "0"),
		@ApiImplicitParam(name = "size", value = "Page size", required = false, dataType = "string", paramType = "query", defaultValue = "10"),
		@ApiImplicitParam(name = "sort", value = "Sort expression", required = false, dataType = "string", paramType = "query", example = "name,asc") })
	ResponseEntity<PagedResources<E>> find( 
		@RequestParam(value = "search", required = false, defaultValue = "")
		String search,

		@ApiIgnore
		Pageable pageable);
	
} //@formatter:on

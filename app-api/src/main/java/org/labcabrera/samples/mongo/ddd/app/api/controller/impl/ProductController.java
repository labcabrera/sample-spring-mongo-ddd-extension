package org.labcabrera.samples.mongo.ddd.app.api.controller.impl;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.app.api.repositories.ProductRepository;
import org.labcabrera.samples.mongo.ddd.app.api.resources.ProductResource;
import org.labcabrera.samples.mongo.ddd.app.model.Product;
import org.labcabrera.samples.mongo.ddd.commons.api.SwaggerConfig;
import org.labcabrera.samples.mongo.ddd.commons.api.errors.EntityNotFoundException;
import org.labcabrera.samples.mongo.ddd.commons.api.querydsl.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.data.CustomerRepository;
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
@RequestMapping(value = "/v1/products", produces = "application/hal+json")
@Api(tags = "Products")
public class ProductController {

	private final ProductRepository repository;
	private final PagedResourcesAssembler<Product> pagedAssembler;
	private final ResourceAssembler<Product, ProductResource> assembler;
	private final PredicateParser predicateParser;

	public ProductController(ProductRepository repository, PagedResourcesAssembler<Product> pagedAssembler,
		PredicateParser predicateParser) {
		this.repository = repository;
		this.pagedAssembler = pagedAssembler;
		this.predicateParser = predicateParser;
		assembler = new ResourceAssembler<Product, ProductResource>() {

			@Override
			public ProductResource toResource(Product entity) {
				return new ProductResource(entity);
			}
		};
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Product search by id",
		authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	public ResponseEntity<ProductResource> findById(@PathVariable String id) {
		return repository.findById(id).map(p -> ResponseEntity.ok(assembler.toResource(p)))
			.orElseThrow(() -> new EntityNotFoundException("Missing entity " + id));
	}

	//@formatter:off
	@GetMapping
	@ApiOperation(value = "Product search", authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "Page number", required = false, dataType = "string", paramType = "query", defaultValue = "0"),
		@ApiImplicitParam(name = "size", value = "Page size", required = false, dataType = "string", paramType = "query", defaultValue = "10"),
		@ApiImplicitParam(name = "sort", value = "Sort expression", required = false, dataType = "string", paramType = "query", example = "name,asc") })
	//@formatter:on
	public ResponseEntity<PagedResources<ProductResource>> find(
		@RequestParam(name = "search", required = false, defaultValue = "") String search,
		@ApiIgnore @PageableDefault(sort = { "name", "surname" }) Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, CustomerRepository.PATH_MAP);
		Page<Product> page = predicate.isPresent() ? repository.findAll(predicate.get(), pageable)
			: repository.findAll(pageable);
		return ResponseEntity.ok(pagedAssembler.toResource(page, assembler));
	}
}

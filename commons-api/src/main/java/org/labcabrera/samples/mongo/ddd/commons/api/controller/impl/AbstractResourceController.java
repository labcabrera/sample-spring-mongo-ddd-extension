package org.labcabrera.samples.mongo.ddd.commons.api.controller.impl;

import java.util.Map;
import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.commons.api.errors.EntityNotFoundException;
import org.labcabrera.samples.mongo.ddd.commons.api.querydsl.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.model.security.HasAuthorization;
import org.labcabrera.samples.mongo.ddd.commons.service.AbstractSecurityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;

import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@AllArgsConstructor
public abstract class AbstractResourceController<E extends HasAuthorization, R extends ResourceSupport> {

	protected final AbstractSecurityService<E> service;
	protected final PagedResourcesAssembler<E> pagedAssembler;
	protected final ResourceAssemblerSupport<E, R> assembler;
	protected final PredicateParser predicateParser;

	public ResponseEntity<R> findById(@PathVariable String id) {
		return service.findById(id).map(p -> ResponseEntity.ok(assembler.toResource(p)))
			.orElseThrow(() -> new EntityNotFoundException("Missing entity " + id));
	}

	public ResponseEntity<PagedResources<R>> find(
		@RequestParam(name = "search", required = false, defaultValue = "") String search,
		@ApiIgnore Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, getEntityPaths());
		Page<E> page = predicate.isPresent() ? service.findAll(predicate.get(), pageable) : service.findAll(pageable);
		return ResponseEntity.ok(pagedAssembler.toResource(page, assembler));
	}

	@SuppressWarnings("rawtypes")
	protected abstract Map<String, Path> getEntityPaths();
}

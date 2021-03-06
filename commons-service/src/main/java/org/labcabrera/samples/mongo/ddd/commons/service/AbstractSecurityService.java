package org.labcabrera.samples.mongo.ddd.commons.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.labcabrera.samples.mongo.ddd.commons.data.ApiRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.security.HasAuthorization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.StringPath;

public abstract class AbstractSecurityService<T extends HasAuthorization> {

	protected final ApiRepository<T> repository;

	public AbstractSecurityService(ApiRepository<T> repository) {
		this.repository = repository;
	}

	protected abstract ListPath<String, StringPath> getAutorizationPaths();

	public Optional<T> findById(String id) {
		Optional<T> optional = repository.findById(id);
		if (!optional.isPresent()) {
			return optional;
		}
		T entity = (T) optional.get();
		List<String> permissions = readPermissions();
		if (entity.getAuthorization() == null || entity.getAuthorization().isEmpty()
			|| permissions.contains(HasAuthorization.ROOT)) {
			return optional;
		}
		for (String i : entity.getAuthorization()) {
			if (permissions.contains(i)) {
				return optional;
			}
		}
		throw new AccessDeniedException("Access denied");
	}

	public Page<T> findAll(Pageable pageable) {
		return findAll(null, pageable);
	}

	public Page<T> findAll(Predicate predicate, Pageable pageable) {
		Predicate newPredicate = compositePredicate(predicate);
		if (newPredicate != null) {
			return repository.findAll(newPredicate, pageable);
		}
		else {
			return repository.findAll(pageable);
		}
	}

	protected List<String> readPermissions() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		if (auth == null || auth.getAuthorities() == null) {
			throw new AccessDeniedException("Invalid security context");
		}
		return auth.getAuthorities().stream().map(x -> x.getAuthority()).collect(Collectors.toList());
	}

	/**
	 * Combina el <code>Predicate</code> solicitado por el usuario con las reestricciones de seguridad basadas en el
	 * contexto de seguridad.
	 * @param predicate
	 * @return
	 */
	protected Predicate compositePredicate(Predicate predicate) {
		List<String> permissions = readPermissions();
		if (permissions == null || permissions.isEmpty()) {
			throw new AccessDeniedException("Missing permissions");
		}
		if (permissions.contains(HasAuthorization.ROOT)) {
			return predicate;
		}

		ListPath<String, StringPath> paths = getAutorizationPaths();
		List<Predicate> predicates = new ArrayList<>();
		for (String i : permissions) {
			predicates.add(paths.contains(i));
		}
		Predicate securityPredicate = ExpressionUtils.anyOf(predicates);
		if (predicate == null) {
			return securityPredicate;
		}
		else {
			return ExpressionUtils.and(securityPredicate, predicate);
		}
	}

}

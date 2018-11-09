package org.labcabrera.samples.mongo.ddd.commons.service;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.commons.data.ContractRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

@Service
public class ContractService {

	@Autowired
	private ContractRepository repository;

	public Optional<Contract> findById(String id) {
		Optional<Contract> optional = repository.findById(id);
		if (optional.isPresent()) {
			// TODO filter permissions
		}
		return optional;
	}

	public Page<Contract> findAll(Predicate predicate, Pageable pageable) {
		return null;
	}

	public Page<Contract> findAll(Pageable pageable) {
		return null;
	}

}

package org.labcabrera.samples.mongo.ddd.commons.service;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.commons.data.ContractRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;

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

}

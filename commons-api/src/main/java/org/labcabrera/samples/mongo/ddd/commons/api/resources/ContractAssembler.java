package org.labcabrera.samples.mongo.ddd.commons.api.resources;

import org.labcabrera.samples.mongo.ddd.commons.controller.ContractControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ContractAssembler extends ResourceAssemblerSupport<Contract, ContractResource> {

	public ContractAssembler() {
		super(ContractControllerDefinition.class, ContractResource.class);
	}

	@Override
	public ContractResource toResource(Contract entity) {
		return new ContractResource(entity);
	}

}

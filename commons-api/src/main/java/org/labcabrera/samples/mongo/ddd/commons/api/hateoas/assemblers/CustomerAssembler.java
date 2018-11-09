package org.labcabrera.samples.mongo.ddd.commons.api.hateoas.assemblers;

import org.labcabrera.samples.mongo.ddd.commons.api.controller.CustomerControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.api.hateoas.resources.CustomerResource;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CustomerAssembler extends ResourceAssemblerSupport<Customer, CustomerResource> {

	public CustomerAssembler() {
		super(CustomerControllerDefinition.class, CustomerResource.class);
	}

	@Override
	public CustomerResource toResource(Customer entity) {
		return new CustomerResource(entity);
	}

}

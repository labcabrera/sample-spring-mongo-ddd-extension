package org.labcabrera.samples.mongo.ddd.app.api.hateoas.resources;

import org.labcabrera.samples.mongo.ddd.commons.api.hateoas.resources.CustomerResource;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;

public class AppCustomerResource extends CustomerResource {

	public AppCustomerResource(Customer entity) {
		super(entity);
		// TODO extra links
	}

}

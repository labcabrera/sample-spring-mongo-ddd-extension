package org.labcabrera.samples.mongo.ddd.app.api.controller.impl;

import org.labcabrera.samples.mongo.ddd.app.model.CustomerAdditionalData;
import org.labcabrera.samples.mongo.ddd.commons.api.controller.impl.CustomerController;
import org.labcabrera.samples.mongo.ddd.commons.api.hateoas.assemblers.CustomerAssembler;
import org.labcabrera.samples.mongo.ddd.commons.api.querydsl.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.labcabrera.samples.mongo.ddd.commons.service.CustomerService;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppCustomerController extends CustomerController<CustomerAdditionalData> {

	public AppCustomerController( //@formatter:off
			CustomerService<CustomerAdditionalData> service,
			PagedResourcesAssembler<Customer<CustomerAdditionalData>> pagedAssembler,
			CustomerAssembler<CustomerAdditionalData> assembler,
			PredicateParser predicateParser) { //@formatter:on
		super(service, pagedAssembler, assembler, predicateParser);
	}

}

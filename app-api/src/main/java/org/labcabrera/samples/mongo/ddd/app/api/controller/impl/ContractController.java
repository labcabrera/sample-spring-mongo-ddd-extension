package org.labcabrera.samples.mongo.ddd.app.api.controller.impl;

import java.util.Map;

import org.labcabrera.samples.mongo.ddd.app.model.ContractAdditionalData;
import org.labcabrera.samples.mongo.ddd.commons.api.SwaggerConfig;
import org.labcabrera.samples.mongo.ddd.commons.api.controller.impl.AbstractResourceController;
import org.labcabrera.samples.mongo.ddd.commons.api.hateoas.assemblers.ContractAssembler;
import org.labcabrera.samples.mongo.ddd.commons.api.hateoas.resources.ContractResource;
import org.labcabrera.samples.mongo.ddd.commons.api.querydsl.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.data.ContractRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.labcabrera.samples.mongo.ddd.commons.service.ContractService;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Path;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping(value = "/v1/contracts", produces = "application/hal+json")
@Api(tags = "Contracts")
public class ContractController extends AbstractResourceController<Contract<ContractAdditionalData>, ContractResource> {

	public ContractController( //@formatter:off
			ContractService<ContractAdditionalData> service,
			PagedResourcesAssembler<Contract<ContractAdditionalData>> pagedAssembler,
			ContractAssembler<ContractAdditionalData> assembler,
			PredicateParser predicateParser) { //@formatter:on
		super(service, pagedAssembler, assembler, predicateParser);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Contract search by id",
		authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	public ResponseEntity<ContractResource> findById(@PathVariable("id") String id) {
		return super.findById(id);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map<String, Path> getEntityPaths() {
		return ContractRepository.PATH_MAP;
	}
}

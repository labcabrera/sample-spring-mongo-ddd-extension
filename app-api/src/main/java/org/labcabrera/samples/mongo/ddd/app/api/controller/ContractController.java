package org.labcabrera.samples.mongo.ddd.app.api.controller;

import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.app.model.ContractAdditionalData;
import org.labcabrera.samples.mongo.ddd.app.model.CustomerAdditionalData;
import org.labcabrera.samples.mongo.ddd.commons.api.SwaggerConfig;
import org.labcabrera.samples.mongo.ddd.commons.api.controller.ContractControllerDefinition;
import org.labcabrera.samples.mongo.ddd.commons.api.errors.EntityNotFoundException;
import org.labcabrera.samples.mongo.ddd.commons.api.querydsl.PredicateParser;
import org.labcabrera.samples.mongo.ddd.commons.api.resources.ContractCustomerRelationResource;
import org.labcabrera.samples.mongo.ddd.commons.api.resources.ContractResource;
import org.labcabrera.samples.mongo.ddd.commons.data.ContractRelationRepository;
import org.labcabrera.samples.mongo.ddd.commons.data.ContractRepository;
import org.labcabrera.samples.mongo.ddd.commons.model.Contract;
import org.labcabrera.samples.mongo.ddd.commons.model.ContractCustomerRelation;
import org.labcabrera.samples.mongo.ddd.commons.service.ContractRelationService;
import org.labcabrera.samples.mongo.ddd.commons.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class ContractController implements
	ContractControllerDefinition<ContractResource<ContractAdditionalData>, ContractCustomerRelationResource<ContractAdditionalData, CustomerAdditionalData>> {

	@Autowired
	private ContractService<ContractAdditionalData> contractService;

	@Autowired
	private ContractRelationService<ContractAdditionalData, CustomerAdditionalData> relationService;

	@Autowired
	private PagedResourcesAssembler<Contract<ContractAdditionalData>> contractPagedAssembler;

	@Autowired
	private PagedResourcesAssembler<ContractCustomerRelation<ContractAdditionalData, CustomerAdditionalData>> relationPagedAssembler;

	@Autowired
	private PredicateParser predicateParser;

	private final ResourceAssembler<Contract<ContractAdditionalData>, ContractResource<ContractAdditionalData>> contractAssembler;

	private final ResourceAssembler<ContractCustomerRelation<ContractAdditionalData, CustomerAdditionalData>, ContractCustomerRelationResource<ContractAdditionalData, CustomerAdditionalData>> relationAssembler;

	public ContractController() {
		this.contractAssembler = new ResourceAssembler<Contract<ContractAdditionalData>, ContractResource<ContractAdditionalData>>() {

			@Override
			public ContractResource<ContractAdditionalData> toResource(Contract<ContractAdditionalData> entity) {
				return new ContractResource<>(entity);
			}
		};
		this.relationAssembler = new ResourceAssembler<ContractCustomerRelation<ContractAdditionalData, CustomerAdditionalData>, ContractCustomerRelationResource<ContractAdditionalData, CustomerAdditionalData>>() {

			@Override
			public ContractCustomerRelationResource<ContractAdditionalData, CustomerAdditionalData> toResource(
				ContractCustomerRelation<ContractAdditionalData, CustomerAdditionalData> entity) {
				return new ContractCustomerRelationResource<>(entity);
			}
		};
	}

	@Override
	public ResponseEntity<ContractResource<ContractAdditionalData>> findById(@PathVariable("id") String id) {
		return contractService.findById(id).map(e -> ResponseEntity.ok(new ContractResource<>(e)))
			.orElseThrow(() -> new EntityNotFoundException("Missing entity " + id));
	}

	@Override
	public ResponseEntity<PagedResources<ContractResource<ContractAdditionalData>>> find(
		@RequestParam(name = "search", required = false, defaultValue = "") String search,
		@ApiIgnore @PageableDefault(sort = "surname,name") Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, ContractRepository.PATH_MAP);
		Page<Contract<ContractAdditionalData>> page = predicate.isPresent()
			? contractService.findAll(predicate.get(), pageable)
			: contractService.findAll(pageable);
		return ResponseEntity.ok(contractPagedAssembler.toResource(page, contractAssembler));
	}

	@Override
	public ResponseEntity<PagedResources<ContractCustomerRelationResource<ContractAdditionalData, CustomerAdditionalData>>> findContractRelations(
		@PathVariable("id") String id, @ApiIgnore Pageable pageable) {
		String search = "contract.id==" + id;
		pageable = pageable != null ? pageable : PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, ContractRelationRepository.PATH_MAP);
		Page<ContractCustomerRelation<ContractAdditionalData, CustomerAdditionalData>> page = relationService
			.findAll(predicate.get(), pageable);
		return ResponseEntity.ok(relationPagedAssembler.toResource(page, relationAssembler));

	}

	@PostMapping
	@ApiOperation(value = "Contract creation", authorizations = { @Authorization(value = SwaggerConfig.API_KEY_NAME) })
	public ResponseEntity<PagedResources<ContractResource<ContractAdditionalData>>> create(
		@RequestBody Contract<ContractAdditionalData> entity) {

		return null;
	}

}

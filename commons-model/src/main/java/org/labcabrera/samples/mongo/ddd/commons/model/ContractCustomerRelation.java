package org.labcabrera.samples.mongo.ddd.commons.model;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.labcabrera.samples.mongo.ddd.commons.model.security.HasAuthorization;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "contractRelations")
@Getter
@Setter
public class ContractCustomerRelation implements HasAuthorization {

	@DBRef
	@NotNull
	private Contract contract;

	@DBRef
	@NotNull
	private Customer customer;

	@NotNull
	private RelationType type;

	@NotNull
	private BigDecimal percent;

	private List<String> authorization;
}

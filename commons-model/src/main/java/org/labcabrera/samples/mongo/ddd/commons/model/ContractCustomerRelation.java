package org.labcabrera.samples.mongo.ddd.commons.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "contractRelations")
@Getter
@Setter
public class ContractCustomerRelation {

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

}

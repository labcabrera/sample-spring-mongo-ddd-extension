package org.labcabrera.samples.mongo.ddd.commons.model;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.labcabrera.samples.mongo.ddd.commons.model.security.HasAuthorization;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractCustomerRelation<E, T> implements HasAuthorization {

	@DBRef
	@NotNull
	private Contract<E> contract;

	@DBRef
	@NotNull
	private Customer<T> customer;

	@NotNull
	private RelationType type;

	@NotNull
	private BigDecimal percent;

	private List<String> authorization;
}

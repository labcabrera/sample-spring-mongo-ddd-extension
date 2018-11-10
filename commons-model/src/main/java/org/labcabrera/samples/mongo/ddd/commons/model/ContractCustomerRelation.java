package org.labcabrera.samples.mongo.ddd.commons.model;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.labcabrera.samples.mongo.ddd.commons.model.security.HasAuthorization;
import org.labcabrera.samples.mongo.ddd.commons.serialization.BasicCustomerSerializer;
import org.labcabrera.samples.mongo.ddd.commons.serialization.IdSerializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "contractRelations")
@Getter
@Setter
public class ContractCustomerRelation<E, T> implements HasAuthorization {

	@Id
	private String id;

	@DBRef
	@NotNull
	@JsonSerialize(using = IdSerializer.class)
	private Contract<E> contract;

	@DBRef
	@NotNull
	@JsonSerialize(using = BasicCustomerSerializer.class)
	private Customer<T> customer;

	@NotNull
	private RelationType type;

	@NotNull
	private BigDecimal percent;

	private List<String> authorization;
}

package org.labcabrera.samples.mongo.ddd.commons.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.labcabrera.samples.mongo.ddd.commons.model.security.HasAuthorization;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "contracts")
@Getter
@Setter
public class Contract<E> implements HasAuthorization {

	@Id
	private String id;

	@NotNull
	private String contractNumber;

	@NotNull
	private LocalDate effective;

	private List<String> authorization;

	private E additionalData;

}

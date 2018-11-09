package org.labcabrera.samples.mongo.ddd.commons.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "contracts")
@Getter
@Setter
public class Contract {

	@Id
	private String id;

	@NotNull
	private String contractNumber;

	@NotNull
	private LocalDate effective;


}

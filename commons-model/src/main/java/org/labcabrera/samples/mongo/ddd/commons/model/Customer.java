package org.labcabrera.samples.mongo.ddd.commons.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "customers")
@Getter
@Setter
public class Customer {

	@Id
	private String id;

	@NotNull
	private String name;

	@NotNull
	private String surname;

	@NotNull
	private LocalDate birthDate;

	@NotNull
	private IdCard idCard;
}

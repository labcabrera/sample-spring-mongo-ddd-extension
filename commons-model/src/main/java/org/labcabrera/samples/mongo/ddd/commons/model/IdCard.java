package org.labcabrera.samples.mongo.ddd.commons.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdCard {

	@NotNull
	private String number;

	private LocalDate expiration;

}

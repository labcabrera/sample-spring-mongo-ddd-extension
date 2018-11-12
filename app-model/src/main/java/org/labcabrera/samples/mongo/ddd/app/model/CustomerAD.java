package org.labcabrera.samples.mongo.ddd.app.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAD {

	private String code;

	private LocalDate effective;

	@DBRef
	private List<Product> products;
}

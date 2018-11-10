package org.labcabrera.samples.mongo.ddd.app.model;

import java.time.LocalDate;
import java.util.List;

import org.labcabrera.samples.mongo.ddd.commons.model.Customer;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class AppCustomer extends Customer {

	private String code;

	private LocalDate effective;

	@DBRef
	private List<Product> products;

}

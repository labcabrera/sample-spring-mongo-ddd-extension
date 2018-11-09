package org.labcabrera.samples.mongo.ddd.app.model;

import org.labcabrera.samples.mongo.ddd.commons.model.Contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppContract extends Contract {

	private String internalCode;

}

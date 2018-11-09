package org.labcabrera.samples.mongo.ddd.commons.api.predicate.exception;

import lombok.Getter;

@SuppressWarnings("serial")
public class PredicateParseException extends RuntimeException {

	private static final String MSG_ERR = "Error parsing expression '%s'. %s";

	@Getter
	private final String expression;

	public PredicateParseException(String expression, Throwable cause) {
		super(String.format(MSG_ERR, expression, cause.getMessage()), cause);
		this.expression = expression;
	}

}

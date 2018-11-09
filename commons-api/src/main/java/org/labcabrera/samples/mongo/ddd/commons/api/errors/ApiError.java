package org.labcabrera.samples.mongo.ddd.commons.api.errors;

import java.time.LocalDateTime;

import lombok.Getter;

//TODO definir estructura de los mensajes de error
@Getter
public class ApiError {

	private final String code;
	private final String message;
	private final LocalDateTime timestamp;

	public ApiError(String code, String message) {
		this.code = code;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}
}
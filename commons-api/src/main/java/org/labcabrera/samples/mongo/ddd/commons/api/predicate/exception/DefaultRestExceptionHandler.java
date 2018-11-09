package org.labcabrera.samples.mongo.ddd.commons.api.predicate.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class DefaultRestExceptionHandler extends ResponseEntityExceptionHandler {

	// TODO en lugar de tener todos los if-else tener varios controladores ordenados que se encarguen de procesar cada
	// uno de estos errores.
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleException(Exception ex) {
		log.error("Caught exception {}", ex);
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String code = "500";
		String message = ex.getMessage();
		if (AccessDeniedException.class.isAssignableFrom(ex.getClass())) {
			status = HttpStatus.FORBIDDEN;
			code = String.valueOf(status.value());
			message = "Forbidden";
		}
		else if (EntityNotFoundException.class.isAssignableFrom(ex.getClass())) {
			status = HttpStatus.NOT_FOUND;
			code = String.valueOf(status.value());
			message = "Not found";
		}
		else if (HttpClientErrorException.class.equals(ex.getClass())) {
			HttpClientErrorException clientEx = (HttpClientErrorException) ex;
			status = clientEx.getStatusCode();
			code = String.valueOf(status.value());
			message = ex.getMessage();
		}
		ApiError apiError = new ApiError(code, message);
		return new ResponseEntity<Object>(apiError, status);
	}
}
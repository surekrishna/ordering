package com.prive.ordering.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EntityValidationException extends Exception {
	/**
	 * added generated serialVersionUID
	 */
	private static final long serialVersionUID = -1085144412396190211L;

	public EntityValidationException() {
		// no-arg constructor
	}

	public EntityValidationException(String errorMessage) {
		super(errorMessage);
	}

}
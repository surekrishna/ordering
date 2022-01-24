package com.prive.ordering.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidOperationException extends Exception {
	/**
	 * added generated serialVersionUID
	 */
	private static final long serialVersionUID = 7532584450365612264L;

	public InvalidOperationException() {
		// no-arg constructor
	}

	public InvalidOperationException(String errorMessage) {
		super(errorMessage);
	}

}
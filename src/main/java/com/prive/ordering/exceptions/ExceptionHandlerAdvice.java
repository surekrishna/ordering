package com.prive.ordering.exceptions;

import java.time.LocalDateTime;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({EntityValidationException.class}) 
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<ErrorResponse> entityValidationException(EntityValidationException e, HttpServletRequest request, HttpServletResponse response) {
		var errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getMessage());
		errorResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		errorResponse.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler({ServiceUnavailableException.class})
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public ResponseEntity<ErrorResponse> serviceUnavailableException(ServiceUnavailableException e, HttpServletRequest request, HttpServletResponse response) {
		var errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getMessage());
		errorResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
		errorResponse.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler({NotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> notFoundException(NotFoundException e, HttpServletRequest request, HttpServletResponse response) {
		var errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getMessage());
		errorResponse.setStatus(HttpStatus.NOT_FOUND);
		errorResponse.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({InvalidOperationException.class})
	@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
	public ResponseEntity<ErrorResponse> invalidOperationException(InvalidOperationException e, HttpServletRequest request, HttpServletResponse response) {
		var errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getMessage());
		errorResponse.setStatus(HttpStatus.NOT_IMPLEMENTED);
		errorResponse.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_IMPLEMENTED);
	}
	
	@ExceptionHandler({Exception.class}) 
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> exception(Exception e, HttpServletRequest request, HttpServletResponse response) {
		var errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getMessage());
		errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		errorResponse.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

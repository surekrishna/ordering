package com.prive.ordering.validators;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Component;

import com.prive.ordering.exceptions.EntityValidationException;
import com.prive.ordering.model.Order;

@Component
public class OrderValidator {

	public void validateOrder(Order order) throws EntityValidationException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		if (!constraintViolations.isEmpty())
			throw new EntityValidationException(constraintViolations.iterator().next().getMessage());
	}
}

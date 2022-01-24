package com.prive.ordering.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prive.ordering.enums.Status;
import com.prive.ordering.exceptions.InvalidOperationException;
import com.prive.ordering.exceptions.NotFoundException;
import com.prive.ordering.model.Order;
import com.prive.ordering.repository.OrderRepository;
import com.prive.ordering.service.BrokerCallbackService;
import com.prive.ordering.validators.OrderValidator;

@Service("brokerCallbackService")
public class BrokerCallbackServiceImpl implements BrokerCallbackService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderValidator orderValidator;

	@Override
	public void updateStatus(String id, String reqId, Status status) throws Exception {
		Order existingOrder = this.loadExistingOrderByIdOrReqId(id, reqId);//Loading Order either by id/reqId

		this.validateStatusRules(existingOrder, status);//Validating the Status Rule

		orderValidator.validateOrder(existingOrder);
		orderRepository.save(existingOrder);
	}

	private Order loadExistingOrderByIdOrReqId(String id, String reqId) throws NotFoundException {
		Order existing = null;

		if (null != id) {
			existing = orderRepository.findById(id).orElse(null);
			if (null == existing)
				throw new NotFoundException("Can't find Order with id = " + id);
		} else {
			existing = orderRepository.findByReqId(reqId);
			if (null == existing)
				throw new NotFoundException("Can't find Order with reqId = " + reqId);
		}

		return existing;
	}

	private void validateStatusRules(Order existingOrder, Status status) throws InvalidOperationException {
		if (Status.ACCEPTED == status) {
			if (Status.PLACED == existingOrder.getStatus())
				existingOrder.setStatus(Status.QUEUED);
			else if (Status.QUEUED == existingOrder.getStatus())
				existingOrder.setStatus(Status.FULFILLED);
			else if (Status.REJECTED == existingOrder.getStatus())
				throw new InvalidOperationException("The Order is already REJECTED!");
			else if (Status.FULFILLED == existingOrder.getStatus())
				throw new InvalidOperationException("The Order is already FULLFILLED");
		} else {
			if (Status.REJECTED == existingOrder.getStatus())
				throw new InvalidOperationException("The Order is already REJECTED!");
			else if (Status.FULFILLED == existingOrder.getStatus())
				throw new InvalidOperationException("The Order is already FULLFILLED");

			existingOrder.setStatus(Status.REJECTED);
		}
	}

}

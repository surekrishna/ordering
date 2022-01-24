package com.prive.ordering.service.impl;

import java.util.List;
import java.util.UUID;

import javax.naming.ServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.prive.ordering.dto.BrokerOrderDto;
import com.prive.ordering.enums.Status;
import com.prive.ordering.exceptions.EntityValidationException;
import com.prive.ordering.model.Order;
import com.prive.ordering.repository.OrderRepository;
import com.prive.ordering.service.OrderService;
import com.prive.ordering.validators.OrderValidator;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderValidator orderValidator;

	@Override
	public Order create(Order order) throws ServiceUnavailableException, EntityValidationException {
		order.setStatus(Status.PLACED);//setting order status to PLACED

		orderValidator.validateOrder(order);//Validating the user inputs.

		// calling broker's create-order api
		String orderId = String.valueOf(UUID.randomUUID());
		String requestId = String.valueOf(UUID.randomUUID());

		try {
			restTemplate.postForLocation("http://localhost:9080/create-order", new BrokerOrderDto(orderId, requestId));
		} catch (Exception e) {
			throw new ServiceUnavailableException("Encountered Error while calling the broker-service /create-order api");
		}
		
		// setting the id,reqId and saving
		order.setId(orderId);
		order.setReqId(requestId);
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAll() throws Exception {
		return orderRepository.findAll();
	}

}

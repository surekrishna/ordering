package com.prive.ordering.service;

import java.util.List;

import com.prive.ordering.model.Order;

public interface OrderService {

	Order create(Order order) throws Exception;
	
	List<Order> getAll() throws Exception;
}

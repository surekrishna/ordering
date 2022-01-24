package com.prive.ordering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prive.ordering.model.Order;
import com.prive.ordering.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<String> create(@RequestBody Order order) throws Exception {
		return new ResponseEntity<>(orderService.create(order).getId(), HttpStatus.CREATED);
	}
	
	@GetMapping
	public List<Order> getAll() throws Exception {
		return orderService.getAll();
	}
}

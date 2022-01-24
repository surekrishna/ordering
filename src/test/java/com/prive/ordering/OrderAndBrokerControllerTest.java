package com.prive.ordering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;

import javax.naming.ServiceUnavailableException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prive.ordering.enums.OrdersType;
import com.prive.ordering.enums.Status;
import com.prive.ordering.exceptions.EntityValidationException;
import com.prive.ordering.exceptions.NotFoundException;
import com.prive.ordering.model.Order;
import com.prive.ordering.service.BrokerCallbackService;
import com.prive.ordering.service.OrderService;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderAndBrokerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private BrokerCallbackService brokerCallbackService;
	
	private String orderId = null;

	@Test
	@org.junit.jupiter.api.Order(1)
	void createOrderShouldReturnOrderIdAsString() throws Exception {
		var order = new Order();
		order.setCode("AAPL");
		order.setPrice(new BigDecimal(213));
		order.setQty(100);
		order.setType(OrdersType.LIMIT);

		MvcResult result = mockMvc.perform(post("/orders")
									.contentType(MediaType.APPLICATION_JSON)
									.content(objectMapper.writeValueAsString(order)))
									.andDo(print())
									.andExpect(status().isCreated())
									.andReturn();

		orderId = result.getResponse().getContentAsString();
		assertEquals(36, orderId.length());
	}

	@Test
	@org.junit.jupiter.api.Order(2)
	void getOrdersArray() throws Exception {
		mockMvc.perform(get("/orders"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)));
	}
	
	@Test
	@org.junit.jupiter.api.Order(3)
	@DependsOn("createOrderShouldReturnOrderIdAsString")
	void placedToQueued() throws Exception {
		mockMvc.perform(post("/broker-callback?id="+orderId+"&status="+Status.ACCEPTED))
				.andDo(print())
				.andExpect(status().isNotFound());//replace with isNotFound() to isOk() when you are using MySQL or any RDBMS
	}
	
	@Test
	@org.junit.jupiter.api.Order(4)
	@DependsOn("placedToQueued")
	void queuedToFullfilled() throws Exception {
		mockMvc.perform(post("/broker-callback?id="+orderId+"&status="+Status.ACCEPTED))
				.andDo(print())
				.andExpect(status().isNotFound());//replace with isNotFound() to isOk() when you are using MySQL or any RDBMS
	}
	
	@Test
	@org.junit.jupiter.api.Order(5)
	@DependsOn("queuedToFullfilled")
	void fullFilledToReject() throws Exception {
		mockMvc.perform(post("/broker-callback?id="+orderId+"&status="+Status.REJECTED))
				.andDo(print())
				.andExpect(status().isNotFound());//replace with isNotFound() to isOk() when you are using MySQL or any RDBMS
	}

	@Test
	@org.junit.jupiter.api.Order(6)
	void brokerCallbackStatusUpdate() throws Exception {
		String id = "8c9784d1-74c3-4904-ab2f-6c92e0000051";
		NotFoundException ex = assertThrows(NotFoundException.class, () -> {
												brokerCallbackService.updateStatus(id, null, Status.ACCEPTED);
											});

		assertTrue(true, ex.getMessage().concat("Can't find Order with id = " + id));
	}

	@Test
	@org.junit.jupiter.api.Order(7)
	void brokerCallbackFinalStatusUpdate() throws Exception {
		String reqId = "8c0000d1-74c3-4904-ab2f-6c92e0000051";
		NotFoundException ex = assertThrows(NotFoundException.class, () -> {
												brokerCallbackService.updateStatus(null, reqId, Status.ACCEPTED);
											});

		assertTrue(true, ex.getMessage().concat("Can't find Order with reqId = " + reqId));
	}
	
	@Test
	@org.junit.jupiter.api.Order(8)
	void createOrderShouldReturnEntityValidationException() throws Exception {
		var order = new Order();
		order.setCode("AAPL");
		order.setPrice(new BigDecimal(213));
		order.setQty(null);
		order.setType(OrdersType.LIMIT);

		EntityValidationException ex = assertThrows(EntityValidationException.class, () -> {
															orderService.create(order);
													});

		assertTrue(true, ex.getMessage().concat("Please provide qty value."));
	}
	
	//@Test //To run this test case /create-order server should be down.
	@org.junit.jupiter.api.Order(9)
	void serviceUnavailable() throws Exception {
		var order = new Order();
		order.setCode("AAPL");
		order.setPrice(new BigDecimal(213));
		order.setQty(100);
		order.setType(OrdersType.LIMIT);

		ServiceUnavailableException ex = assertThrows(ServiceUnavailableException.class, () -> {
			orderService.create(order);
		});
		
		assertTrue(true, ex.getMessage().concat("Encountered Error while calling the broker-service /create-order api"));
	}

}

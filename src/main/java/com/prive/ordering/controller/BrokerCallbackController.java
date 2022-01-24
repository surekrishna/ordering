package com.prive.ordering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prive.ordering.enums.Status;
import com.prive.ordering.service.BrokerCallbackService;

@RestController
@RequestMapping("/broker-callback")
public class BrokerCallbackController {

	@Autowired
	private BrokerCallbackService brokerCallbackService;

	@PostMapping(params = {"id"})
	public void brokerCallbackById(@RequestParam(required = true) String id, @RequestParam(required = true) Status status) throws Exception {
		brokerCallbackService.updateStatus(id, null, status);
	}
	
	@PostMapping(params = {"req_id"})
	public void brokerCallbackByReqId(@RequestParam(name = "req_id", required = true) String reqId, @RequestParam(required = true) Status status) throws Exception {
		brokerCallbackService.updateStatus(null, reqId, status);
	}
}

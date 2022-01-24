package com.prive.ordering.service;

import com.prive.ordering.enums.Status;

public interface BrokerCallbackService {

	void updateStatus(String id, String reqId, Status status) throws Exception;
	
}

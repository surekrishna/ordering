package com.prive.ordering.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BrokerOrderDto implements Serializable {
	/**
	 * added generated serialVersionUID
	 */
	private static final long serialVersionUID = -2913763638101534424L;
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("req_id")
	private String reqId;

	public BrokerOrderDto(String id, String reqId) {
		this.id = id;
		this.reqId = reqId;
	}

	//getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReqId() {
		return reqId;
	}
	
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	
}

package com.prive.ordering.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.prive.ordering.enums.OrdersType;
import com.prive.ordering.enums.Status;

@Entity
@Table(name = "stock_order")
public class Order implements Serializable {
	/**
	 * added generated serialVersionUID
	 */
	private static final long serialVersionUID = 1959381599944450877L;

	@Id
	@Size(max = 36, message = "id should be at most {max} characters.")
	@Column(name = "id", columnDefinition = "varchar(36)", nullable = false)
	private String id;
	
	@Size(max = 36, message = "reqId should be at most {max} characters.")
	@Column(name = "req_id", columnDefinition = "varchar(36)", nullable = false)
	private String reqId;

	@NotNull(message = "Please provide qty value.")
	@Positive(message = "Please provide positive qty value.")
	@Column(name = "qty", columnDefinition = "int4", nullable = false)
	private Integer qty;

	@NotNull(message = "Please provide code value.")
	@Size(max = 4, message = "Please enter code at most {max} characters.")
	@Column(name = "code", columnDefinition = "varchar(4)", nullable = false)
	private String code;

	@NotNull(message = "Please provide price value.")
	@Positive(message = "Please provide positive price value.")
	@Column(name = "price", columnDefinition = "DECIMAL(10,4)", nullable = false)
	private BigDecimal price;

	@NotNull(message = "Please provide type value.")
	@Enumerated(EnumType.STRING)
	@Column(name = "type", columnDefinition = "varchar(10)", nullable = false)
	private OrdersType type;
	
	@NotNull(message = "Please provide status value.")
	@Enumerated(EnumType.STRING)
	@Column(name = "status", columnDefinition = "varchar(10)", nullable = false)
	private Status status;

	public Order() {
		// no-arg constructor
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

	public OrdersType getType() {
		return type;
	}

	public void setType(OrdersType type) {
		this.type = type;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}

package com.prive.ordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prive.ordering.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

	@Query("SELECT o FROM Order o WHERE o.reqId IS :reqId")
	Order findByReqId(@Param("reqId") String reqId);
}

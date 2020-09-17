package com.verizon.trainingdemo.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.verizon.trainingdemo.order.entities.Order;

@Repository
public interface OrderDataRepository extends JpaRepository<Order, Long> {

	
}

package com.verizon.trainingdemo.order.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.verizon.trainingdemo.order.dto.OrderDto;
import com.verizon.trainingdemo.order.dto.OrderReqData;
import com.verizon.trainingdemo.order.entities.Order;

@RequestMapping("/api/v1")
public interface OrderContoller {
	
	@GetMapping(path="/orders/order/{orderId}" , produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<String> getOrderDetails(@PathVariable("orderId") Long id );
	
	@PostMapping(path="/orders/order/" , produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<String> placeOrder(@RequestBody OrderReqData data );
	
	@GetMapping(path="/orders/order" , produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<String> getAllOrders();
	
	
}

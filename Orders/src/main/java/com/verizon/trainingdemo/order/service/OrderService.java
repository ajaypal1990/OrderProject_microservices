package com.verizon.trainingdemo.order.service;

import java.net.URISyntaxException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.verizon.trainingdemo.order.dto.OrderReqData;
import com.verizon.trainingdemo.order.entities.Order;
import com.verizon.trainingdemo.order.exception.DataNotFoundException;
import com.verizon.trainingdemo.order.exception.RequestDataException;


public interface OrderService {

	public Object placeOrder(OrderReqData orderReqData) throws  RequestDataException, URISyntaxException, DataNotFoundException, JsonProcessingException;
	public Order getOrderDetails(Long id) throws Exception;
	public List<Order> getAllOrder() throws Exception;
	
}

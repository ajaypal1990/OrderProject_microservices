package com.verizon.trainingdemo.shipping.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verizon.trainingdemo.shipping.entity.Shipping;
import com.verizon.trainingdemo.shipping.exception.RecordsNotFoundException;
import com.verizon.trainingdemo.shipping.service.ShippingService;
import com.verizon.trainingdemo.shipping.util.Constants;
import com.verizon.trainingdemo.shipping.util.ResponseMessage;
import com.verizon.trainingdemo.shipping.util.ShippingDto;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(value="/api/v1/shipping", produces = "application/json")
public class ShippingController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required=true)
	ShippingService service;
	/*
	 * @Autowired OrderConsumer consumer;
	 */
	
	/*
	 * @PostMapping("/order") public ResponseMessage
	 * saveShippingDetails(@RequestBody ShippingDto dto) { ResponseMessage message =
	 * new ResponseMessage(); service.saveShippingDetails(dto); message.getData();
	 * message.setMessage("data saved successfully");
	 * message.setStatus(Constants.STATUS_SUCCESS);
	 * message.setStatusCode(Constants.SUCCESS_CODE); return message; }
	 */
	
	@GetMapping("/shippingDetails")
	public List<Shipping> getAllShippingDetails()
	{
		List<Shipping> shippingList = service.getAll();
		if(shippingList==null || shippingList.isEmpty())
		{

			throw new RecordsNotFoundException("No Records available");
			
		}
		
		return shippingList;
			
	
	}
	

}

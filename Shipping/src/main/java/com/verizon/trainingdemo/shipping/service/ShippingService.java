package com.verizon.trainingdemo.shipping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.verizon.trainingdemo.shipping.entity.Shipping;
import com.verizon.trainingdemo.shipping.util.ResponseMessage;
import com.verizon.trainingdemo.shipping.util.ShippingDto;

@Service
public interface ShippingService {

	public List<Shipping> getAll();
	
	public ResponseMessage saveShippingDetails(ShippingDto dto);
	
}

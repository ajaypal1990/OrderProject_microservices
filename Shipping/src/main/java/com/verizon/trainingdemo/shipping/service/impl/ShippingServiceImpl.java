package com.verizon.trainingdemo.shipping.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verizon.trainingdemo.shipping.entity.Shipping;
import com.verizon.trainingdemo.shipping.exception.CustomException;
import com.verizon.trainingdemo.shipping.repository.ShippingRepository;
import com.verizon.trainingdemo.shipping.service.ShippingService;
import com.verizon.trainingdemo.shipping.util.Constants;
import com.verizon.trainingdemo.shipping.util.ResponseMessage;
import com.verizon.trainingdemo.shipping.util.ShippingDto;

@Service
public class ShippingServiceImpl implements ShippingService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ShippingRepository repository;

	@Override
	public List<Shipping> getAll() {
		List<Shipping> shippingList = new ArrayList<>();
		repository.findAll().forEach(list -> shippingList.add(list));

		if (shippingList == null || shippingList.isEmpty()) {
			log.info("no data found");
		}
		return shippingList;
	}

	@Override
	public ResponseMessage saveShippingDetails(ShippingDto dto) {
		ResponseMessage message = new ResponseMessage();
		try {
			Shipping shipping = new Shipping();
			shipping.setOrderId(dto.getOrderId());
			shipping.setDate(dto.getDate());
			shipping.setUserAddress(dto.getUserAddress());
			shipping.setOrderPrice(dto.getOrderPrice());
			repository.save(shipping);
			message.setMessage("data saved successfully");
			message.setStatus(Constants.STATUS_SUCCESS);
			message.setStatusCode(Constants.SUCCESS_CODE);
		} catch (Exception e) {

			throw new CustomException("Record cannot be saved");
		}
		return message;
	}
}

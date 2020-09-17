package com.verizon.trainingdemo.shipping.jmsConfig;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.verizon.trainingdemo.shipping.entity.Order;
import com.verizon.trainingdemo.shipping.service.ShippingService;
import com.verizon.trainingdemo.shipping.util.Constants;
import com.verizon.trainingdemo.shipping.util.JsontoObjectMapperUtil;
import com.verizon.trainingdemo.shipping.util.ShippingDto;

@Component
public class OrderConsumer {
	
	@Autowired
	ShippingService service;
	private static Logger log = LoggerFactory.getLogger(OrderConsumer.class);

	@JmsListener(destination = Constants.ORDER_TOPIC, containerFactory = "topicListenerFactory")
	public Message receiveTopicMessage(@Payload String msg, @Headers MessageHeaders headers, Message message,
			Session session) throws JMSException {
		ShippingDto dto = null;
		try {
			Order order  = (Order) JsontoObjectMapperUtil.toObject(Order.class , msg);
		
		 dto = new ShippingDto();
		dto.setOrderId(order.getOrderId());
		dto.setDate(order.getOrderCreationDate());
		dto.setUserAddress(order.getUserAddress());
		dto.setOrderPrice(order.getTotalPrice());
		log.info("received <" + order + ">");
		
		
		service.saveShippingDetails(dto);
		message.acknowledge();
		} catch (JsonMappingException e) {
			log.info ("not acknowledged");
		} catch (JsonProcessingException e) {
			log.info("Error has occured");
		}
		return message;
		
	}

}
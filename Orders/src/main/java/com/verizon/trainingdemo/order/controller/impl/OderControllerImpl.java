package com.verizon.trainingdemo.order.controller.impl;

import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.verizon.trainingdemo.order.controller.OrderContoller;
import com.verizon.trainingdemo.order.dto.OrderDto;
import com.verizon.trainingdemo.order.dto.OrderReqData;
import com.verizon.trainingdemo.order.entities.Order;
import com.verizon.trainingdemo.order.exception.DataNotFoundException;
import com.verizon.trainingdemo.order.exception.RequestDataException;
import com.verizon.trainingdemo.order.service.OrderService;
import com.verizon.trainingdemo.order.util.JsontoObjectMapperUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
public class OderControllerImpl implements OrderContoller {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OrderService orderService;

	@GetMapping("/orders/order/{orderId}")
	@ApiOperation(value = "Get Single Order Details", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully got the order detail"),
			@ApiResponse(code = 404, message = "Resource Not Found"),
			@ApiResponse(code = 500, message = "Please try again afer sometime") })

	public ResponseEntity<String> getOrderDetails(@PathVariable("orderId") Long id) {
		String response = null;
		try {
			response = JsontoObjectMapperUtil.toJson(orderService.getOrderDetails(id));
		} catch (DataNotFoundException e1) {

			log.error("Data not found" + e1.getMessage());
			return new ResponseEntity<String>("{Data not Found}", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Error Occured", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@PostMapping("/orders/order")
	@ApiOperation(value = "Place an Order ", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully placed the order"),
			@ApiResponse(code = 404, message = "Resource Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error. Please try again afer sometime") })

	public ResponseEntity<String> placeOrder(OrderReqData data) {
		String response = null;
		try {
			response = JsontoObjectMapperUtil.toJson(orderService.placeOrder(data));
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} catch (RequestDataException | URISyntaxException e1) {
			log.error("Error occured", e1);
			e1.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error occured", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/orders/order")
	@ApiOperation(value = "Get Multiple Order Details", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully got all the order detail"),
			@ApiResponse(code = 404, message = "Resource Not Found"),
			@ApiResponse(code = 500, message = "Please try again afer sometime") })
	
	public ResponseEntity<String> getAllOrders() {
		String response = null;
		try {
			response = JsontoObjectMapperUtil.toJson(orderService.getAllOrder());
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} catch (DataNotFoundException  e1) {
			e1.printStackTrace();
			log.error("Error occured", e1);
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND.toString(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error occured", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

}

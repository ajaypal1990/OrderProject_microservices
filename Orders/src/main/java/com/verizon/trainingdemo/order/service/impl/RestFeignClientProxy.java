package com.verizon.trainingdemo.order.service.impl;

import java.net.URI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.verizon.trainingdemo.order.dto.Inventory;
import com.verizon.trainingdemo.order.dto.JWTRequestDto;
import com.verizon.trainingdemo.order.dto.JWTToken;
import com.verizon.trainingdemo.order.dto.Product;
import com.verizon.trainingdemo.order.dto.User;

import feign.Headers;

@FeignClient( name="restFeignClientProxy" , url ="http://ecommerce.com" )
public interface RestFeignClientProxy {
	
	@GetMapping(path="/api/v1/products/{productId}")
	public Product getProductDetail( URI baseURI , @PathVariable Long productId );
	
	@GetMapping(path="/api/v1/inventory/{productId}")
	public Inventory getInventory( URI baseURI , @PathVariable Long productId );
	
	
	@GetMapping(path="/api/v1/user/{userId}")
	@Headers("Authorization: {jwtToken}")
	public User getUserDetail( URI baseURI , @PathVariable("userId") Long userId, @RequestHeader("Authorization") String jwtToken);

	@PutMapping(path="/api/v1/inventory/{productId}/quantity/{quantity}",consumes="application/json")
	public Inventory updateInventory ( URI baseURI , @PathVariable Long productId, @PathVariable Long quantity);
	
	@PostMapping(path="/api/v1/user/authenticate" , consumes="application/json")
	public JWTToken getJwtToken ( URI baseURI , @RequestBody JWTRequestDto dto );
}

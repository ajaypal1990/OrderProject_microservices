package com.verizon.trainingdemo.products.service;


import java.util.List;
import java.util.Optional;

import com.verizon.trainingdemo.products.entity.ProductDto;
import com.verizon.trainingdemo.products.model.ProductPayload;


public interface ProductService {
	
	ProductDto getProduct(String productid);
	
	List<ProductDto> getAllProducts();

	void deleteProduct(String productid);
	
	ProductDto createProduct(ProductPayload payload );

	ProductDto updateProduct(String productId, ProductDto product);
	
}

package com.verizon.trainingdemo.products.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verizon.trainingdemo.products.entity.ProductDto;
import com.verizon.trainingdemo.products.entity.ProductRepository;
import com.verizon.trainingdemo.products.exception.ProductServiceException;
import com.verizon.trainingdemo.products.model.ProductPayload;
import com.verizon.trainingdemo.products.util.Constants;
import com.verizon.trainingdemo.products.util.Utility;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository repository;

	@Override
	public ProductDto getProduct(String productid) {
		Optional<ProductDto> product = repository.findById(productid);
		if (!product.isPresent())
			throw new ProductServiceException(Constants.RECORD_NOT_FOUND);

		return product.get();
	}

	@Override
	public void deleteProduct(String productid) {
		Optional<ProductDto> product = repository.findById(productid);
		if (!product.isPresent())
			throw new ProductServiceException(Constants.RECORD_NOT_FOUND);
		repository.deleteById(productid);
	}


	@Override
	public ProductDto updateProduct(String productId, ProductDto product) {
		Optional<ProductDto> productOptional = repository.findById(productId);	
		if (!productOptional.isPresent())
			throw new ProductServiceException(Constants.RECORD_NOT_FOUND);
		product.setProductId(productId);
		repository.save(product);
		return product;
	}

	@Override
	public ProductDto createProduct(ProductPayload payload) {
		String productId = Utility.generateProductId();
		ProductDto product = new ProductDto(productId, payload.getName(), payload.getDescription(), payload.getPrice());
		repository.save(product);
		return product;
	}

	@Override
	public List<ProductDto> getAllProducts() {
		List<ProductDto> products = (List<ProductDto>) repository.findAll();
		if ( Utility.isEmpty(products) || products.size() == 0 )
			throw new ProductServiceException(Constants.RECORD_NOT_FOUND);
		
		return products;
	}
	
}

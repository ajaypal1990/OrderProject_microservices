package com.verizon.trainingdemo.products.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verizon.trainingdemo.products.entity.ProductDto;
import com.verizon.trainingdemo.products.exception.ProductServiceException;
import com.verizon.trainingdemo.products.model.ProductPayload;
import com.verizon.trainingdemo.products.service.ProductService;
import com.verizon.trainingdemo.products.util.Constants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/")
	@ApiOperation(value = "Save Product ", response = ProductDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product created succesfully."),
			@ApiResponse(code = 404, message = "Resource Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error. Please try again afer sometime") })

	public ProductDto saveProduct(ProductPayload payload) {
		try {
			return productService.createProduct(payload); 
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductServiceException(Constants.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping("/{productId}")
	@ApiOperation(value = "Get Single Product Details",  response = ProductDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully got the Product detail"),
			@ApiResponse(code = 404, message = "Resource Not Found"),
			@ApiResponse(code = 500, message = "Please try again afer sometime") })

	public ProductDto getProductDetails(@PathVariable("productId") String id) {
		try {
			return productService.getProduct(id); 
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductServiceException(Constants.INTERNAL_SERVER_ERROR);
		}
	}
	

	@GetMapping("/")
	@ApiOperation(value = "Get All Products",  response = ProductDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "All products fetched successfully."),
			@ApiResponse(code = 404, message = "Resource Not Found"),
			@ApiResponse(code = 500, message = "Please try again afer sometime") })

	public List<ProductDto> getAllProducts(@PathVariable("productId") String id ) {
		try {
			return productService.getAllProducts();
		} catch (Exception e) {
			throw new ProductServiceException(Constants.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{productId}")
	@ApiOperation(value = "Get Single Product Details", response = ProductDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product updated successfully"),
			@ApiResponse(code = 404, message = "Resource Not Found"),
			@ApiResponse(code = 500, message = "Please try again afer sometime") })

	public ProductDto updateProduct(@PathVariable("productId") String id, ProductDto payload ) {
		try {
			return productService.updateProduct(id, payload); 
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductServiceException(Constants.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@DeleteMapping("/{productId}")
	@ApiOperation(value = "Delete Product", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product deleted successfully"),
			@ApiResponse(code = 404, message = "Resource Not Found"),
			@ApiResponse(code = 500, message = "Please try again afer sometime") })

	public ResponseEntity<Object> deleteProduct(@PathVariable("productId") String id ) {
		try {
		    productService.deleteProduct(id); 
		 	return ResponseEntity.noContent().build();
		} catch (Exception e) {
			throw new ProductServiceException(Constants.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	

}
package com.verizon.trainingdemo.products.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "products")
@Table(name = "products")
public class ProductDto {
	@Id
	@Column(nullable = false)
	private String productId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private double price;

	
	public ProductDto() {
		super();
	}
	public ProductDto(String productId, String name, String description, double price) {
		super();
		this.name = name;
		this.productId = productId;
		this.description = description;
		this.price = price;
	}
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}


	
}

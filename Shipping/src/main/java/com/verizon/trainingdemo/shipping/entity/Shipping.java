package com.verizon.trainingdemo.shipping.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Shipping {
	@Id
	@Column(name = "ShippingId")
	@GeneratedValue
	private Long shpippingId;
	
	@Column(name= "OrderId")
	private  Long orderId;
	
	@Column(name = "OrderCreationDate")
	private Date date;
	
	@Column(name = "UserAddress")
	private String userAddress;
	
	@Column(name = "OrderPrice")
	private float orderPrice;
	
	
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public float getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(float orderPrice) {
		this.orderPrice = orderPrice;
	}
	public Long getShpippingId() {
		return shpippingId;
	}
	public void setShpippingId(Long bigInteger) {
		this.shpippingId = bigInteger;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long bigInteger) {
		this.orderId = bigInteger;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}

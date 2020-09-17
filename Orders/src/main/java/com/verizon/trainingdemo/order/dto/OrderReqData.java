package com.verizon.trainingdemo.order.dto;

public class OrderReqData {

	private Long productId;
	private Long userId;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public boolean isValid() {
		return productId != null && userId != null;
	}
		
}

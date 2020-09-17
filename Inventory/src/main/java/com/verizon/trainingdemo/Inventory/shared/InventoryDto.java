package com.verizon.trainingdemo.Inventory.shared;

public class InventoryDto {
	private int inventoryId;
	private String productId;
	private int quantity;
	public InventoryDto() {
		super();
	}
	public InventoryDto(int inventoryId, String productId, int quantity) {
		super();
		this.inventoryId = inventoryId;
		this.productId = productId;
		this.quantity = quantity;
	}
	public int getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "InventoryDto [inventoryId=" + inventoryId + ", productId=" + productId + ", quantity=" + quantity + "]";
	}
	
	
	
}

package com.verizon.trainingdemo.Inventory.reponse;

public class InventoryRest {
	private int inventoryId;
	private String productId;
	private int quantity;
	
	
	public InventoryRest() {
		super();
	}
	public InventoryRest(int inventoryId, String productId, int quantity) {
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
		return "InventoryRest [inventoryId=" + inventoryId + ", productId=" + productId + ", quantity=" + quantity
				+ "]";
	}
	
	
	
	
	
}

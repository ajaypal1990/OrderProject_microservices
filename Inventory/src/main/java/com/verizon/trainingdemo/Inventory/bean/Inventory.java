package com.verizon.trainingdemo.Inventory.bean;

public class Inventory {
	private int inventoryId;
	private String productid;
	private int quantity;
	
	
	
	public Inventory() {
		super();
	}

	public Inventory(int inventoryId, String productid, int quantity) {
		super();
		this.inventoryId = inventoryId;
		this.productid = productid;
		this.quantity = quantity;
	}
	
	public int getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}

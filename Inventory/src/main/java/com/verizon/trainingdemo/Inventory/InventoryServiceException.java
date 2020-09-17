package com.verizon.trainingdemo.Inventory;

public class InventoryServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InventoryServiceException(String message) {
		super(message);
		
	}
}

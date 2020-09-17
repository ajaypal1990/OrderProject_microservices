package com.verizon.trainingdemo.Inventory.service;

import com.verizon.trainingdemo.Inventory.shared.InventoryDto;

public interface InventoryService {
	InventoryDto getInventory(String productid);

	String deleteInventoryByProductId(String productid);
	
	InventoryDto updateInventoryByProductId(String productid,int quantity);
	
}

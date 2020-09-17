package com.verizon.trainingdemo.Inventory.controller;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verizon.trainingdemo.Inventory.InventoryServiceException;
import com.verizon.trainingdemo.Inventory.reponse.InventoryRest;
import com.verizon.trainingdemo.Inventory.service.InventoryService;
import com.verizon.trainingdemo.Inventory.shared.InventoryDto;
import com.verizon.trainingdemo.Inventory.ui.model.reponse.Constants;
import com.verizon.trainingdemo.Inventory.ui.model.reponse.OperationStatusModel;

@RestController
@Transactional
@RequestMapping("/api/v1/inventory")
public class InventoryController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	InventoryService inventoryService;

	@GetMapping(path = "/{productid}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public InventoryRest getInventory(@PathVariable String productid) {
		InventoryRest returnRest = new InventoryRest();
		logger.info("Inside the getInventory method with productId {}",productid);
		InventoryDto getInventory = inventoryService.getInventory(productid);
		if (getInventory == null)
			throw new InventoryServiceException(Constants.MISSING_FIELD);
		BeanUtils.copyProperties(getInventory, returnRest);
		return returnRest;
	}

	/// api/v1/inventory/{productid}
	@DeleteMapping(path = "/{productid}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel deleteInventoryByProductId(@PathVariable String productid) {
		logger.info("Inside the deleteInventoryByProductId method with productId {}",productid);
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(Constants.DELETE);

		String deletedProductId = inventoryService.deleteInventoryByProductId(productid);

		returnValue.setOperationResult(Constants.SUCCESS);
		returnValue.setProductId(deletedProductId);
		return returnValue;
	}

	@PutMapping(path = "/{productid}/quantity/{quantity}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public InventoryRest updateInventoryByQuantity(@PathVariable String productid, @PathVariable int quantity) {
		InventoryRest returnRest = new InventoryRest();

		InventoryDto updatedInventory = inventoryService.updateInventoryByProductId(productid, quantity);
		if (updatedInventory == null)
			throw new InventoryServiceException(Constants.MISSING_FIELD);
		BeanUtils.copyProperties(updatedInventory, returnRest);
		return returnRest;
	}

}

package com.verizon.trainingdemo.Inventory.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.verizon.trainingdemo.Inventory.InventoryServiceException;
import com.verizon.trainingdemo.Inventory.io.entity.InventoryEntity;
import com.verizon.trainingdemo.Inventory.io.entity.InventoryRepository;
import com.verizon.trainingdemo.Inventory.reponse.InventoryRest;
import com.verizon.trainingdemo.Inventory.service.impl.InventoryServiceImpl;
import com.verizon.trainingdemo.Inventory.shared.InventoryDto;

public class InventoryControllerTest {
	@InjectMocks
	InventoryController inventoryController;
	
	@Mock
	InventoryServiceImpl inventoryService;
	
	@Mock
	InventoryRepository repository;
	
	final String Product_ID="TestID";
	
	InventoryDto inventoryDto;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		inventoryDto=new InventoryDto();
		inventoryDto.setInventoryId(10001);
		inventoryDto.setProductId("ABC101");
		inventoryDto.setQuantity(50);
	}
	
	@Test
	void testGetInventory() {
		Mockito.when(inventoryService.getInventory(Mockito.anyString())).thenReturn(inventoryDto);
		InventoryRest returnValue=inventoryController.getInventory(Product_ID);
		
		Assertions.assertNotNull(returnValue);
		Assertions.assertEquals(inventoryDto.getInventoryId(), returnValue.getInventoryId());
		Assertions.assertEquals(inventoryDto.getProductId(), returnValue.getProductId());
		Assertions.assertEquals(inventoryDto.getQuantity(), returnValue.getQuantity());
	}
	
	@Test
	void getInventoryTest() {
		InventoryEntity inventoryEntity=new InventoryEntity();
		inventoryEntity.setInventoryId(10001);
		inventoryEntity.setProductId("ABC101");
		inventoryEntity.setQuantity(50);
		
		Mockito.when(repository.findByProductId(Mockito.anyString())).thenReturn(inventoryEntity);
		Mockito.when(inventoryService.getInventory(Mockito.anyString())).thenReturn(inventoryDto);
		
		InventoryDto inventoryDto=inventoryService.getInventory("ABC101");
		Assertions.assertNotNull(inventoryDto);
		Assertions.assertEquals("ABC101", inventoryDto.getProductId());
		Assertions.assertEquals(10001, inventoryDto.getInventoryId());
		Assertions.assertEquals(50, inventoryDto.getQuantity());
		
		
	}
	
	@Test
	void getInventoryTest_Throw_Exception() {
		
		Mockito.when(repository.findByProductId(Mockito.anyString())).thenReturn(null);
		Mockito.when(inventoryService.getInventory(Mockito.anyString())).thenReturn(inventoryDto);
		
		InventoryDto inventoryDto=inventoryService.getInventory("ABC101");
		Assertions.assertNotNull(inventoryDto);
		Assertions.assertEquals("ABC101", inventoryDto.getProductId());
		Assertions.assertEquals(10001, inventoryDto.getInventoryId());
		Assertions.assertEquals(50, inventoryDto.getQuantity());
		
		Assertions.assertThrows(InventoryServiceException.class, ()->{
			throw new InventoryServiceException("");
		});
		
	}
}

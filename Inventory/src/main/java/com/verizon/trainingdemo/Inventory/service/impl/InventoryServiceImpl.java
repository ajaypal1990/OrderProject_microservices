package com.verizon.trainingdemo.Inventory.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verizon.trainingdemo.Inventory.InventoryServiceException;
import com.verizon.trainingdemo.Inventory.io.entity.InventoryEntity;
import com.verizon.trainingdemo.Inventory.io.entity.InventoryRepository;
import com.verizon.trainingdemo.Inventory.service.InventoryService;
import com.verizon.trainingdemo.Inventory.shared.InventoryDto;
import com.verizon.trainingdemo.Inventory.ui.model.reponse.Constants;

@Service
public class InventoryServiceImpl implements InventoryService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private InventoryRepository repository;
	ModelMapper modelMapper = new ModelMapper();

	@Override
	public InventoryDto getInventory(String productid) {
		InventoryDto returnDTO = new InventoryDto();
		logger.info("Inside the getInventory method with productId {} ",productid);
		InventoryEntity inventoryEntity = repository.findByProductId(productid);

		if (inventoryEntity == null)
			throw new InventoryServiceException(Constants.RECORD_NOT_FOUND);

		BeanUtils.copyProperties(inventoryEntity, returnDTO);
		// modelMapper.map(inventoryEntity, InventoryDto.class);

		return returnDTO;
	}

	@Override
	public String deleteInventoryByProductId(String productid) {
		logger.info("Inside the deleteInventoryByProductId method with productId:"+productid);
		InventoryEntity inventoryEntity = repository.findByProductId(productid);
		if (inventoryEntity == null)
			throw new InventoryServiceException(Constants.RECORD_NOT_FOUND);

		repository.deleteByProductId(productid);

		return productid;
	}

	public InventoryDto updateInventoryByProductId(String productid, int quantity) {
		logger.info("Inside the updateInventoryByProductId method with productId:"+productid);
		InventoryDto returnDTO = new InventoryDto();
		InventoryEntity inventoryEntity = repository.findByProductId(productid);
		
		if (inventoryEntity == null)
			throw new InventoryServiceException(Constants.RECORD_NOT_FOUND);
		
		int availableQuantity = inventoryEntity.getQuantity();
		
		if (quantity > 0 && (availableQuantity - quantity >= 0)) {
			availableQuantity = availableQuantity - quantity;
			inventoryEntity.setQuantity(availableQuantity);
			repository.save(inventoryEntity);
			BeanUtils.copyProperties(inventoryEntity, returnDTO);
			return returnDTO;
		}else {
			throw new InventoryServiceException(Constants.QUANTITY_NOT_AVAILABLE +availableQuantity);
		}
		
	}

}

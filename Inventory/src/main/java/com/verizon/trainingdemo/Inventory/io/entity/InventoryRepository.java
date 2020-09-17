package com.verizon.trainingdemo.Inventory.io.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface InventoryRepository extends CrudRepository<InventoryEntity, Integer>{
	
	InventoryEntity findByProductId(String id);
	
	void deleteByProductId(String productid);
	
}

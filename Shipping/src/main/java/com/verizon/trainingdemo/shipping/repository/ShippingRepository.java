package com.verizon.trainingdemo.shipping.repository;


import org.springframework.data.repository.CrudRepository;

import com.verizon.trainingdemo.shipping.entity.Shipping;

public interface ShippingRepository extends CrudRepository<Shipping, Integer>{

	

}

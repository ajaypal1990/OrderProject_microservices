package com.verizon.trainingdemo.shipping.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.verizon.trainingdemo.shipping.entity.Shipping;
import com.verizon.trainingdemo.shipping.service.ShippingService;

@SpringBootTest
class ShippingApplicationTests extends AbstractTest {

	private Shipping shipping1;
	private Shipping shipping2;
	private Shipping shipping3;

	@Autowired
	private ShippingService service;

	Date date = new Date();

	@Before(value = "")
	public void intializeTestEnvironment() throws Exception {

		this.shipping1 = new Shipping();
		shipping1.setShpippingId(Long.valueOf(1000));
		shipping1.setOrderId(Long.valueOf(10000));
		shipping1.setOrderPrice(5000);
		shipping1.setUserAddress("New Delhi");
		shipping1.setDate(date);

		this.shipping2 = new Shipping();
		shipping2.setShpippingId(Long.valueOf(1000));
		shipping2.setOrderId(Long.valueOf(12346));
		shipping2.setOrderPrice(1200);
		shipping2.setUserAddress("Mumbai");
		shipping2.setDate(date);

		this.shipping3 = new Shipping();
		shipping3.setShpippingId(Long.valueOf(66575));
		shipping3.setOrderId(Long.valueOf(65433));
		shipping3.setOrderPrice(7789);
		shipping3.setUserAddress("Indore");
		shipping3.setDate(date);

	}

	@Test
	public void testShippingDetails() throws Exception {
		List<Shipping> listShipping = new ArrayList<>();
		listShipping.add(shipping1);
		listShipping.add(shipping2);
		listShipping.add(shipping3);

		// assertEquals(listShipping , service.getAll());
	}

	@Test
	void contextLoads() {
	}

	@Override
	@Before(value = "")
	public void setUp() {
		super.setUp();
	}

	@Test
	public void getShippingList() throws Exception {
		String uri = "/getAllShippingDetails";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		Assert.assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Shipping[] list = super.mapFromJson(content, Shipping[].class);
		Assert.assertTrue(list.length > 0);
	}

}

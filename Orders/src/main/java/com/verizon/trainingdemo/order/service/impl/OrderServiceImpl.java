package com.verizon.trainingdemo.order.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.verizon.trainingdemo.order.dto.Inventory;
import com.verizon.trainingdemo.order.dto.JWTRequestDto;
import com.verizon.trainingdemo.order.dto.JWTToken;
import com.verizon.trainingdemo.order.dto.OrderDto;
import com.verizon.trainingdemo.order.dto.OrderReqData;
import com.verizon.trainingdemo.order.dto.Product;
import com.verizon.trainingdemo.order.dto.User;
import com.verizon.trainingdemo.order.entities.Order;
import com.verizon.trainingdemo.order.exception.DataNotFoundException;
import com.verizon.trainingdemo.order.exception.RequestDataException;
import com.verizon.trainingdemo.order.repository.OrderDataRepository;
import com.verizon.trainingdemo.order.service.OrderService;
import com.verizon.trainingdemo.order.util.JsontoObjectMapperUtil;
import com.verizon.trainingdemo.order.util.Utility;

@Service
public class OrderServiceImpl implements OrderService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${userservice.username}")
	private String username;

	@Value("${userservice.password}")
	private String password;

	@Value("${redis.key.order}")
	private String redisKeyOrder;

	@Value("${redis.key.allOrder}")
	private String redisKeyAllOrder;

	@Value("${activemq.topic}")
	private String jmsTopic;

	@Value("${ecommerce.product.url}")
	private String productBaseUrl;
	@Value("${ecommerce.inventory.url}")
	private String inventoryBaseUrl;

	@Value("${ecommerce.user.url}")
	private String userBaseUrl;

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	RestFeignClientProxy restFeignClientProxy;

	@Autowired
	OrderDataRepository orderDataRepository;

	public Object placeOrder(OrderReqData orderReqData)
			throws RequestDataException, URISyntaxException, DataNotFoundException, JsonProcessingException {
		OrderDto orderDto = null;

		if (Utility.isEmpty(orderReqData) || !orderReqData.isValid())
			throw new RequestDataException("Data cannot be empty");

		// CompletableFuture<Optional<Product>> getProduct(String productBaseUrl , Long
		// )

		CompletableFuture<Optional<Product>> future = CompletableFuture.supplyAsync(new Supplier<Optional<Product>>() {
			
			@Override
			public Optional<Product> get() {
				
					//Optional<Product> productOptional = 
					try {
						return Optional.ofNullable(
								restFeignClientProxy.getProductDetail(new URI(productBaseUrl), orderReqData.getProductId()));
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new RuntimeException();
					}
					
			}
		});

		// Block and get the result of the Future
		Optional<Product> productOptional = null;
		try {
			productOptional = future.get();
			if (!productOptional.isPresent()) {
				log.info("product Not Found");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
/*
		Optional<Product> productOptional = Optional.ofNullable(
				restFeignClientProxy.getProductDetail(new URI(productBaseUrl), orderReqData.getProductId()));
		if (!productOptional.isPresent()) {
			log.info("product Not Found");
		}
*/
		Optional<Inventory> inventoryOptional = Optional
				.ofNullable(restFeignClientProxy.getInventory(new URI(inventoryBaseUrl), orderReqData.getProductId()));
		if (!inventoryOptional.isPresent()) {
			log.info("inventory Not Found");
		}

		Inventory inventory = inventoryOptional.get();
		if (inventory.getQuantity() <= 0)
			throw new DataNotFoundException("Product Doesnt Exist in Inventory");

		JWTRequestDto requestDto = new JWTRequestDto(username, password);
		System.out.println("requestDto " +requestDto);
		Optional<JWTToken> jwtTokenOptional = Optional
				.ofNullable(restFeignClientProxy.getJwtToken(new URI(userBaseUrl), requestDto));
		if (!jwtTokenOptional.isPresent()) {
			log.info("inventory Not Found");
		}
		String token = jwtTokenOptional.get().getToken();
		System.out.println("token is " + token);
		Optional<User> userOptional = Optional
				.ofNullable(restFeignClientProxy.getUserDetail(new URI(userBaseUrl), orderReqData.getUserId(), token));
		if (!userOptional.isPresent()) {
			log.info("User Not Found");
		}

		Optional<Inventory> updateInventoryOptional = Optional.ofNullable(
				restFeignClientProxy.updateInventory(new URI(inventoryBaseUrl), orderReqData.getProductId(), 1L));
		if (!updateInventoryOptional.isPresent()) {
			log.info("inventory Not Found");
		}

		Date date = new Date();
		Long orderId = insertDataIntoDatabase(productOptional.get(), date);

		orderDto = pushDatatoShippingService(productOptional.get(), inventoryOptional.get(), userOptional.get(), date,
				orderId);

		return orderDto;
	}

	public Long getUserIdFromToken() {

		return 1L;
	}

	public OrderDto pushDatatoShippingService(Product product, Inventory inventory, User user, Date creationDate,
			Long orderId) throws JmsException, JsonProcessingException {
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderCreationDate(creationDate);
		orderDto.setOrderId(orderId);
		orderDto.setProductId(product.getId());
		orderDto.setProductName(product.getName());
		orderDto.setProductPrice(product.getPrice());
		orderDto.setUserAddress(user.getAddress());
		orderDto.setTotalPrice(product.getPrice());
		orderDto.setUserId(user.getUserId());
		log.info("Sending Data to JmsTopic " + orderDto);
		jmsTemplate.convertAndSend(jmsTopic, JsontoObjectMapperUtil.toJson(orderDto));
		return orderDto;

	}

	public Long insertDataIntoDatabase(Product product, Date creationDate) {

		Order order = new Order(creationDate, product.getId(), product.getPrice());
		order = orderDataRepository.save(order);
		return order.getOrderId();

	}

	public Order getOrderDetails(Long id) throws Exception {
		Order orderCacheObj = (Order) redisTemplate.opsForValue().get(redisKeyOrder + id);
		if (orderCacheObj != null) {
			System.out.println("Getting data from the Cache");
			log.info("Getting data from Cache");
			return orderCacheObj;
		}

		Optional<Order> order = orderDataRepository.findById(id);
		if (!order.isPresent())
			throw new DataNotFoundException("Data Not Found Exception");

		redisTemplate.opsForValue().set(redisKeyOrder + id, order.get());
		return order.get();
	}

	@Override
	public List<Order> getAllOrder() throws Exception {

		List<Order> orderObjectList = (List<Order>) redisTemplate.opsForValue().get(redisKeyAllOrder);
		if (orderObjectList != null) {
			System.out.println("Getting data from Cache");
			log.info("Getting data from Cache");
			return orderObjectList;
		}

		List<Order> order = orderDataRepository.findAll();
		if (Utility.isEmpty(order) || order.size() == 0)
			throw new DataNotFoundException("Data Not Found Exception");

		redisTemplate.opsForValue().set(redisKeyAllOrder, order);

		return order;
	}

}

package com.verizon.trainingdemo.user.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.verizon.trainingdemo.user.entities.User;
import com.verizon.trainingdemo.user.exception.RecordNotAvailableException;
import com.verizon.trainingdemo.user.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/users")
	public List<User> getUsers(){
		List<User> listUsers = userService.getAllUser();
		if(listUsers == null || listUsers.isEmpty()) {
			logger.info("unable to fetch users from user table");
			throw new RecordNotAvailableException("No User added");
		}
		return listUsers;
	}
	
	@GetMapping("/{userId}")
	public User getUsersById(@PathVariable Long userId){
		
		User user = null;
		try {
			user = userService.getUserById(userId);
		} catch (Exception e) {
			logger.info("unable to fetch user  for the Id=" + userId);
			throw e;
		}
		return user;
	}
	 
	@PostMapping("/user")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		try {
			User newUser = userService.createUser(user);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{userId}")
					.buildAndExpand(newUser.getUserId())
					.toUri();
			logger.debug("{}"+user+ " is created");
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			logger.info("unable to create user");
			throw e;
		}
	}
	

}

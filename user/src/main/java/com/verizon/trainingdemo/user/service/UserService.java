package com.verizon.trainingdemo.user.service;

import java.util.List;

import com.verizon.trainingdemo.user.entities.User;
import com.verizon.trainingdemo.user.exception.RecordNotFoundException;


public interface UserService {
	
	public User createUser(User user);

	public List<User> getAllUser();

	public User getUserById(Long userId) throws RecordNotFoundException;

	public User validateUser(User user) throws RecordNotFoundException;

}

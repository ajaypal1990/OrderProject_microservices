package com.verizon.trainingdemo.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verizon.trainingdemo.user.entities.User;
import com.verizon.trainingdemo.user.exception.CustomException;
import com.verizon.trainingdemo.user.exception.RecordNotFoundException;
import com.verizon.trainingdemo.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	public UserRepository repo;

	@Override
	public User createUser(User user) {
		try {
			repo.save(user);
		}catch (Exception e) {
			throw new CustomException("Unable to create Dublicate User");
		}
		return user;
	}

	@Override
	public List<User> getAllUser() {
		return repo.findAll();
	}

	@Override
	public User getUserById(Long userId) throws RecordNotFoundException {
		Optional<User> user = repo.findById(userId);
		if(user != null && user.get() != null) {
			return user.get();
		}
		throw new RecordNotFoundException("No user for by Id= " + userId);
	}

	@Override
	public User validateUser(User user) throws RecordNotFoundException {
		User newUser = repo.findByUserName(user.getUserName());
		if(newUser != null) {
			return newUser;
		}
		throw new RecordNotFoundException("No user for by User Name= " + user.getUserName());
	}
	
	

}

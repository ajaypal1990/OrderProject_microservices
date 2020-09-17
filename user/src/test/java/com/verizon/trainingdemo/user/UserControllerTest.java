package com.verizon.trainingdemo.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.verizon.trainingdemo.user.controller.UserController;
import com.verizon.trainingdemo.user.entities.User;
import com.verizon.trainingdemo.user.exception.RecordNotFoundException;
import com.verizon.trainingdemo.user.service.UserServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerTest {
	@InjectMocks
	UserController userController;
	
	@Mock
	UserServiceImpl userService;
	
	@Autowired 
	private MockMvc mvc;
	
	final List<User> userList= new ArrayList<User>();
	
	@BeforeAll
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
        User user=new User();
		user.setUserName("shub123");
		user.setPassword("test!1234");
		user.setUserId(1L);
		userList.add(user);
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
        
    }
	
	
	@Test
	void testGetAllUsers() {
		Mockito.when(userService.getAllUser()).thenReturn(userList);
		List<User> users = userController.getUsers();
		assertEquals(userList.get(0).getName(), users.get(0).getName());
		assertEquals(userList.get(0).getPassword(), users.get(0).getPassword());
		assertEquals(userList.get(0).getUserId(), users.get(0).getUserId());
	}
	@Test
	void testGetAllUsers_Null() {
		try {
			Mockito.when(userService.getAllUser()).thenReturn(null);
			List<User> users = userController.getUsers();
		} catch(Exception e) {
			assertEquals("No User added", e.getMessage());
		}
		
	}
	@Test
	void testGetUserById() {
		User user=new User();
		user.setUserName("shub1234");
		user.setPassword("test!12345");
		user.setUserId(2L);
		userList.add(user);
		Mockito.when(userService.getUserById(2L)).thenReturn(userList.get(1));
		User users = userController.getUsersById(2L);
		assertEquals(userList.get(1).getName(), users.getName());
		assertEquals(userList.get(1).getPassword(), users.getPassword());
		assertEquals(userList.get(1).getUserId(), users.getUserId());
	}
	
	@Test
	void testGetUserById_Excep() {
		Mockito.when(userService.getUserById(1L)).thenThrow(new RecordNotFoundException("No user for by Id= " + 1L));
		try {
			User users = userController.getUsersById(1L);
		} catch (Exception e) {
			assertEquals("No user for by Id= 1", e.getMessage());
		}
	}
	
}

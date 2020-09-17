package com.verizon.trainingdemo.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import com.verizon.trainingdemo.user.controller.JWTTokenController;
import com.verizon.trainingdemo.user.entities.User;
import com.verizon.trainingdemo.user.service.UserServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
public class JWTTokenControllerTest {
	@InjectMocks
	JWTTokenController jwtTokenController;
	
	@Mock
	UserServiceImpl userService;
	
	@Mock
	private Configuration configuration;
	
	@Autowired 
	private MockMvc mvc;
	
	private User user;
	
	final List<User> userList= new ArrayList<User>();
	
	@BeforeAll
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
        user=new User();
		user.setUserName("shub12");
		user.setPassword("test!1234");
		user.setUserId(1L);
		userList.add(user);
        mvc = MockMvcBuilders.standaloneSetup(jwtTokenController).build();
        
    }
	
	
	@Test
	void testLogin() {
		Mockito.when(userService.validateUser(userList.get(0))).thenReturn(userList.get(0));
		Mockito.when(configuration.getSecretkey()).thenReturn("MySecretKey");
		User users = jwtTokenController.login(user);
		assertEquals(userList.get(0).getName(), users.getName());
		assertNotNull(users.getToken());
	}
	
	
}

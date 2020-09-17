package com.verizon.trainingdemo.user.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verizon.trainingdemo.user.Configuration;
import com.verizon.trainingdemo.user.entities.User;
import com.verizon.trainingdemo.user.exception.CustomException;
import com.verizon.trainingdemo.user.exception.RecordNotFoundException;
import com.verizon.trainingdemo.user.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api/v1/user")
public class JWTTokenController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Configuration configuration;
	@Autowired
	private UserService userService;

	@PostMapping("/authenticate")
	public User login(@RequestBody User user) {
		try {
			validateUser(user);
		} catch (Exception e) {
			logger.info("Validation failed");
			throw e;
		}
		String token = getJWTToken(user);
		User newUser = new User();
		newUser.setUserName(user.getUserName());
		newUser.setToken(token);		
		return newUser;
		
	}

	private void validateUser(User user) throws RecordNotFoundException, CustomException {
		try {
			
			User newUser = userService.validateUser(user);
			if(!newUser.getPassword().equals(user.getPassword())) {
				throw new CustomException("Password did not match");
			}
		} catch (RecordNotFoundException e) {
			logger.info("unable to get user by username " + user.getUserName());
			throw e;
		} catch (CustomException e) {
			logger.info("unable to validate user");
			throw e;
		}
		
	}

	private String getJWTToken(User user) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(user.getUserName())
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						configuration.getSecretkey().getBytes()).compact();

		return "Bearer " + token;
	}
}
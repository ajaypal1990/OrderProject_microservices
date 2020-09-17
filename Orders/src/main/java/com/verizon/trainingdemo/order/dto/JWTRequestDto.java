package com.verizon.trainingdemo.order.dto;

public class JWTRequestDto {

	private String userName;
	private String password;
	public JWTRequestDto() {
		
	}
	public JWTRequestDto(String userName, String password) {
		
		this.userName=userName;
		this.password =password;
		
	}
	public String getuserName() {
		return userName;
	}
	public void setuserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "JWTRequestDto [userName=" + userName + ", password=" + password + "]";
	}
	
	
	
}

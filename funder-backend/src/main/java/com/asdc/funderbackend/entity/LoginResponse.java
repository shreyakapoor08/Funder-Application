package com.asdc.funderbackend.entity;

public class LoginResponse {
	
	Long id;
	String firstName;
	String jwtToken;
	String userName;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getToken() {
		return jwtToken;
	}
	public void setToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}

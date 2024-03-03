package com.asdc.funderbackend.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.asdc.funderbackend.entity.ResetPassword;
import com.asdc.funderbackend.entity.User;
import com.asdc.funderbackend.service.UserService;
import com.asdc.funderbackend.serviceImpl.EmailService;
import com.asdc.funderbackend.serviceImpl.UserDetailService;
import com.asdc.funderbackend.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@ActiveProfiles("qa")
@SpringBootTest
public class UserControllerIntegrationTest {
	
	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserService userService;
	
	@Mock
	private UserService userServiceImpl;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private UserDetailService userDetailService;
	
	@Mock
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Mock 
	private JwtUtil jwtUtil;
	
	@Autowired 
	private MockMvc mockMvc;
	  
	@Autowired 
	private ObjectMapper objectMapper;

	@Test
	public void resetPasswordTestSuccess() throws JsonProcessingException, Exception {
		ResetPassword resetPassword = new ResetPassword();
		resetPassword.setToken("123456");
		resetPassword.setNewPassword("acsdd");
		mockMvc.perform(put("/user/reset-password").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(resetPassword)))
		.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string("Password Updated Successfully!"));
	}
	
	@Test
    void signupUserNameAlreadyExistTest() throws Exception {
	    User newUser = new User();
	    newUser.setUserName("bhautik5661@gmail.com");

	    when(userService.findByUserName("bhautik5661@gmail.com")).thenReturn(newUser);

	    mockMvc.perform(post("/user/signup")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(newUser)))
	            .andExpect(status().isBadRequest())
	            .andExpect(MockMvcResultMatchers.content().string("UserName already exists"));
	}
  
}

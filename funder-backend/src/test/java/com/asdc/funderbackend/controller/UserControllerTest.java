package com.asdc.funderbackend.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.asdc.funderbackend.entity.LoginRequest;
import com.asdc.funderbackend.entity.ResetPassword;
import com.asdc.funderbackend.entity.User;
import com.asdc.funderbackend.service.UserService;
import com.asdc.funderbackend.serviceImpl.EmailService;
import com.asdc.funderbackend.serviceImpl.UserDetailService;
import com.asdc.funderbackend.serviceImpl.UserServiceImpl;
import com.asdc.funderbackend.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class UserControllerTest {

	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserService userService;
	
	@Mock
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private UserDetailService userDetailService;
	
	@Mock
    private AuthenticationManager authenticationManager;
	
	@Mock 
	private JwtUtil jwtUtil;
	
	private MockMvc mockMvc;
	
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Test
	public void signUpTestSuccessfulTest() {
		User user = new User();
        user.setUserName("user1");
        user.setPassword("12345678");

        Mockito.when(userService.findByUserName(anyString())).thenReturn(null);

        ResponseEntity<?> result = userController.signup(user);

        assertEquals("Registration Successful", result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
	}

	@Test
	public void signUpErrorTest() {
		User user = new User();
        user.setUserName("user1");
        user.setPassword("12345678");
        
        when(userService.findByUserName(anyString())).thenReturn(null);
        
        Mockito.lenient().when(userService.save(user)).thenThrow(new RuntimeException());

        ResponseEntity<?> response = userController.signup(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Please try again", response.getBody());
	}
	
	@Test
	public void signUpUserAlreadyExistsTest() {
		User user = new User();
        user.setUserName("user1");
        user.setPassword("12345678");
        
        Mockito.when(userService.findByUserName(anyString())).thenReturn(user);

        ResponseEntity<?> response = userController.signup(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("UserName already exists", response.getBody());
	}
	
	@Test
	public void loginSuccessful() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserName("user1");
		loginRequest.setPassword("12345678");
		
		User user = new User();
	    user.setFirstName("user1");
		UserDetails userDetails = userDetailService.loadUserByUsername(loginRequest.getUserName());;
		
		Mockito.when(userService.findByUserName(loginRequest.getUserName())).thenReturn(user);
		Mockito.when(userDetailService.loadUserByUsername(anyString())).thenReturn(userDetails);
		Mockito.when(jwtUtil.generateToken(userDetails)).thenReturn("asdfghjkl");

        ResponseEntity<?> response = userController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void loginUserNameNotFound() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserName("user1");
		loginRequest.setPassword("12345678");
		UsernameNotFoundException exception = new UsernameNotFoundException("User not found");
		Mockito.when(userDetailService.loadUserByUsername(anyString())).thenThrow(exception);

        ResponseEntity<?> response = userController.login(loginRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User not found", response.getBody());
	}
	
//	@Test
//	public void loginInvalidCredential() {
//		LoginRequest loginRequest = new LoginRequest();
//		loginRequest.setUserName("user1");
//		loginRequest.setPassword("12345678");
//
//		Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//        .thenThrow(new AuthenticationException("Invalid credentials"));
////        		new  ("Invalid credentials"));
//
//		ResponseEntity<?> response = userController.login(loginRequest);
//
//	    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//	    assertEquals("Invalid username or password", response.getBody());
//	}
	
	@Test
	public void sendEmailSuccess() {
		User user = new User();
		user.setUserName("bkb647087@gmail.com");
        user.setPassword("12345678");
        user.setFirstName("Bk");
        user.setLastName("KB");
        
        Mockito.when(userService.findByUserName("bkb647087@gmail.com")).thenReturn(user);
        Mockito.when(userServiceImpl.generateToken(user)).thenReturn("123456");

        ResponseEntity<String> response = userController.sendEmail("bkb647087@gmail.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email sent successfully!", response.getBody());  
	}
	
	@Test
	public void sendEmailError() {
		User user = new User();
		user.setUserName("bkb647087@gmail.com");

        Mockito.when(userService.findByUserName("bkb647087@gmail.com")).thenReturn(user);
        Mockito.when(userServiceImpl.generateToken(user)).thenThrow(RuntimeException.class);
        
        ResponseEntity<String> response = userController.sendEmail("bkb647087@gmail.com");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Server Error while sending email", response.getBody());
	}
	
	@Test
	public void sendEmailUserNotFound() {
		User user = new User();
		user.setUserName("bkb647087@gmail.com");
		
        Mockito.when(userService.findByUserName(anyString())).thenThrow(UsernameNotFoundException.class);
        ResponseEntity<String> response = userController.sendEmail("bkb647087@gmail.com");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User not found", response.getBody());
	}
	
	@Test
	public void sendEmailUserNotFound2() {
		User user = new User();
		user.setUserName("bkb647087@gmail.com");
		
        Mockito.when(userService.findByUserName(anyString())).thenReturn(null);
        ResponseEntity<String> response = userController.sendEmail("bkb647087@gmail.com");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User not found", response.getBody());
	}

	@Test
	public void resetPasswordTestSuccess() {
		ResetPassword resetPassword = new ResetPassword();
		resetPassword.setToken("123456");
		resetPassword.setNewPassword("acsdd");
		
		Mockito.when(userServiceImpl.isValidToken(resetPassword.getToken())).thenReturn(true);
		ResponseEntity<String> response = userController.resetPassword(resetPassword);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password Updated Successfully!", response.getBody());
	}
	
	@Test
	public void resetPasswordUserNotFoundTest() {
		ResetPassword resetPassword = new ResetPassword();
		resetPassword.setToken("123456");
		resetPassword.setNewPassword("acsdd");

		Mockito.when(userServiceImpl.isValidToken(resetPassword.getToken())).thenThrow(UsernameNotFoundException.class);
		ResponseEntity<String> response = userController.resetPassword(resetPassword);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User not found", response.getBody());
	}
}

package com.asdc.funderbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.asdc.funderbackend.entity.LoginRequest;
import com.asdc.funderbackend.entity.LoginResponse;
import com.asdc.funderbackend.entity.ResetPassword;
import com.asdc.funderbackend.entity.User;
import com.asdc.funderbackend.service.UserService;
import com.asdc.funderbackend.serviceImpl.EmailService;
import com.asdc.funderbackend.serviceImpl.UserServiceImpl;
import com.asdc.funderbackend.util.JwtUtil;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static String RESETPASSWORD="RESETPASSWORD";
    private static String SIGNUP="SIGNUP";

    
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;

	/**
	 * Signup response entity.
	 *
	 * @param user the user
	 * @return the response entity
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user){
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering into signup(){}");
		}
		
		User user1 = userService.findByUserName(user.getUserName());
		if(user1!=null) {
			return ResponseEntity.badRequest().body("UserName already exists");
		}
		
		try {
			userService.save(user);
			emailService.sendEmail(user, "" ,SIGNUP);
		}
		catch (Exception ex) {
			LOGGER.error("Error while registering user(){}", ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please try again");
		}
					
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Exit from signup(){}");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successful");		
	}

	/**
	 * Login response entity.
	 *
	 * @param loginRequest the login request
	 * @return the response entity
	 */
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		
		LoginResponse loginResponse = new LoginResponse();
		User user = new User();
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering into login(){}");
		}
		
        try {
			user = userService.findByUserName(loginRequest.getUserName());
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        } catch (AuthenticationException ex) {
			LOGGER.error("Error while trying to login(){}", ex);
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserName());
        } catch (UsernameNotFoundException ex) {
			LOGGER.error("Error while fetching userDetails", ex);
            return ResponseEntity.badRequest().body("User not found");
        }

        String jwtToken = jwtUtil.generateToken(userDetails);
        loginResponse.setToken(jwtToken);
        loginResponse.setFirstName(user.getFirstName());
        loginResponse.setId(user.getId());
        loginResponse.setUserName(user.getUserName());
        
        if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Exit from login(){}");
		}
        
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

	/**
	 * Send email response entity.
	 *
	 * @param userName the user name
	 * @return the response entity
	 */
	@PostMapping("/send-forgot-password-email")
	public ResponseEntity<String> sendEmail(@RequestParam String userName) {
		User user = new User();
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering into sendEmail(){}");
		}
		
		try {
			user = userService.findByUserName(userName);
		}catch(UsernameNotFoundException ex) {
			LOGGER.error("Error while sending email", ex);
			return ResponseEntity.badRequest().body("User not found");
		}
		
		if(user != null ) {
			try {
				String token = userServiceImpl.generateToken(user);
				emailService.sendEmail(user, token, RESETPASSWORD);
			}catch(Exception e) {
				return ResponseEntity.internalServerError().body("Server Error while sending email");
			}
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");

		}
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Exit from sendEmail(){}");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("Email sent successfully!");
	  }

	/**
	 * Reset password response entity.
	 *
	 * @param resetPassword the reset password
	 * @return the response entity
	 */
	@PutMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody ResetPassword resetPassword){
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering into resetPassword(){}");
		}
		
        try {	
        	if(userServiceImpl.isValidToken(resetPassword.getToken())) {
        		userServiceImpl.resetPassword(resetPassword.getToken(), resetPassword.getNewPassword());
        	}
        } catch (UsernameNotFoundException ex) {
			LOGGER.error("Error while fetching userDetails", ex);
            return ResponseEntity.badRequest().body("User not found");
        }
        
        if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Exit from resetPassword(){}");
		}

		return ResponseEntity.status(HttpStatus.OK).body("Password Updated Successfully!");
	}
	
}

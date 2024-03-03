package com.asdc.funderbackend.serviceImpl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asdc.funderbackend.entity.LoginRequest;
import com.asdc.funderbackend.entity.User;
import com.asdc.funderbackend.repository.UserRepo;
import com.asdc.funderbackend.service.UserService;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final long TOKEN_EXPIRATION_MINUTES = 60;


	@Autowired
	private UserRepo userRepo;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public String generateToken(User user) {

		String token = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES);
        user.setResetPasswordToken(token);
        user.setResetPasswordTokenExpiration(expirationTime);
        userRepo.save(user);
        
        return token;
    }

	@Override
	public boolean isValidToken(String token) {
        User user = userRepo.findByResetPasswordToken(token);

        if (user != null) {
            LocalDateTime expirationTime = user.getResetPasswordTokenExpiration();
            return expirationTime != null && LocalDateTime.now().isBefore(expirationTime);
        }

        return false;
    }
	
	@Override
	public void resetPassword(String token, String newPassword) {
        User user = userRepo.findByResetPasswordToken(token);
        
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetPasswordToken(null);
            user.setResetPasswordTokenExpiration(null);            
            userRepo.save(user);
        }
    }

	@Override
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public User findByUserName(String userName) {
		return userRepo.findByUserName(userName);
	}
}

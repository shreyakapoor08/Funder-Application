package com.asdc.funderbackend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.asdc.funderbackend.entity.User;
import com.asdc.funderbackend.repository.UserRepo;

/**
 * The type User detail service.
 */
@Service
public class UserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(user);
    }
	
}

package com.asdc.funderbackend.serviceImpl;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.asdc.funderbackend.entity.User;

/**
 * The type Custom user details.
 */
@Service
public class CustomUserDetails implements UserDetails {
	
	private User user;

	/**
	 * Instantiates a new Custom user details.
	 */
	public CustomUserDetails() {}

	/**
	 * Instantiates a new Custom user details.
	 *
	 * @param user the user
	 */
	public CustomUserDetails(User user) {
		this.user = user;
	}
	
	 	@Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	 		return Collections.emptyList();
	    }

	    @Override
	    public String getPassword() {
	        return user.getPassword();
	    }

	    @Override
	    public String getUsername() {
	        return user.getUserName();
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true; 
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true; 
	    }

	    @Override
	    public boolean isEnabled() {
	        return true; 
	    }
}

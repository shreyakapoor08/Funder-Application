package com.asdc.funderbackend.service;

import com.asdc.funderbackend.entity.User;

/**
 * The interface User service.
 */
public interface UserService {

	/**
	 * Generate token string.
	 *
	 * @param user the user
	 * @return the string
	 */
	public String generateToken(User user);

	/**
	 * Is valid token boolean.
	 *
	 * @param token the token
	 * @return the boolean
	 */
	public boolean isValidToken(String token);

	/**
	 * Reset password.
	 *
	 * @param token       the token
	 * @param newPassword the new password
	 */
	public void resetPassword(String token, String newPassword);

	/**
	 * Save user.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User save(User user);

	/**
	 * Find by user name user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	public User findByUserName(String userName);
}

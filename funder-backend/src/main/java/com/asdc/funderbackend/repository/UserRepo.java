package com.asdc.funderbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.asdc.funderbackend.entity.User;

/**
 * The interface User repo.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	/**
	 * Find by user name user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	User findByUserName(String userName);

	/**
	 * Find by reset password token user.
	 *
	 * @param token the token
	 * @return the user
	 */
	@Query("SELECT u FROM User u where u.resetPasswordToken=:token")
	public User findByResetPasswordToken(@Param("token") String token);

}

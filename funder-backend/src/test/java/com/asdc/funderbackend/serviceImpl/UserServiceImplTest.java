package com.asdc.funderbackend.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.asdc.funderbackend.entity.User;
import com.asdc.funderbackend.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class UserServiceImplTest {

	private static int TIMING = 30;
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	UserRepo userRepo;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@Test
	public void generateTokenTest() {
		User user = new User();
        user.setUserName("user1");
        user.setPassword("12345678");
        
        Mockito.when(userRepo.save(user)).thenReturn(user);
        String token = userServiceImpl.generateToken(user);
        assertNotNull(token);
        assertNotNull(user.getResetPasswordToken());
        assertNotNull(user.getResetPasswordTokenExpiration());
	}

	@Test
	public void isValidTokenTrueTest() {
		
		User user = new User();
        user.setUserName("user1");
        user.setResetPasswordToken("abcde");
        user.setResetPasswordTokenExpiration(LocalDateTime.now().plusMinutes(TIMING));
        
        Mockito.when(userRepo.findByResetPasswordToken("abcde")).thenReturn(user);
        boolean result = userServiceImpl.isValidToken("abcde");
        assertTrue(result);   
	}
	
	@Test
	public void isValidTokenFalseTest() {
		String token = "abcde";
		Mockito.when(userRepo.findByResetPasswordToken(token)).thenReturn(null);
        boolean result = userServiceImpl.isValidToken(token);
        assertFalse(result);
	}
	
	@Test
	public void resetTokenSuccessTest() {
		User user = new User();
        user.setUserName("user1");
        user.setResetPasswordToken("abcde");
        String newPassword = "1234567";
        
        Mockito.when(userRepo.findByResetPasswordToken("abcde")).thenReturn(user);

        userServiceImpl.resetPassword("abcde", newPassword);
    }
	
	@Test
	public void resetTokenFailTest() {
		User user = new User();
        user.setUserName("user1");
        user.setResetPasswordToken("abcde");
        String newPassword = "1234567";
        
        Mockito.when(userRepo.findByResetPasswordToken("abcde")).thenReturn(null);
        userServiceImpl.resetPassword("abcde", newPassword);
	}
	
	@Test
	public void saveUserTest() {
		User user = new User();
        user.setUserName("user1");
        user.setPassword("12345678");
        
        Mockito.lenient().when(passwordEncoder.encode(user.getPassword())).thenReturn("8786468");
        Mockito.lenient().when(userRepo.save(user)).thenReturn(user);
        
        User user1 = userServiceImpl.save(user); 
        assertEquals("user1",user1.getUserName());
	}
	
	@Test
	public void findByUserNameTest() {
		User user = new User();
		user.setUserName("user1");
		user.setPassword("123456789");
		
		Mockito.when(userRepo.findByUserName("user1")).thenReturn(user);
		User user1 = userServiceImpl.findByUserName("user1");
		assertNotNull(user1);
	}
}

package com.asdc.funderbackend.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.asdc.funderbackend.entity.ContactUsDao;
import com.asdc.funderbackend.service.ContactUsService;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ContactUsServiceImplTest {
	
	@InjectMocks
	ContactUsServiceImpl contactUsServiceImpl;
	
	@Mock
	ContactUsService contactUsService;
	
	@Mock
	EmailService emailService;
	
	 @Test
	 void sendEmailForContactSuccessTest() {
    	 ContactUsDao contactUsDao = new ContactUsDao();
    	 contactUsDao.setName("BK");
    	 contactUsDao.setEmail("BK@gmail.com");
    	 contactUsDao.setMessage("Question 1");
    	 contactUsDao.setSubject("Regarding Doubt");

	     when(emailService.sendEmailForContactUs(contactUsDao)).thenReturn(true);
	     boolean isEmailSent = contactUsServiceImpl.sendEmailForContact(contactUsDao);

	     verify(emailService, times(1)).sendEmailForContactUs(contactUsDao);
	     assertTrue(isEmailSent);
	 }

	 @Test
	 void sendEmailForContactUnSuccessfulTest() {
		 ContactUsDao contactUsDao = new ContactUsDao();
	     contactUsDao.setName("BK");
	     contactUsDao.setEmail("BK@gmail.com");
	   	 contactUsDao.setMessage("Question 1");
	   	 contactUsDao.setSubject("Regarding Doubt");
	    	 
	     lenient().when(emailService.sendEmailForContactUs(contactUsDao)).thenReturn(false);
	     boolean isEmailSent = contactUsService.sendEmailForContact(contactUsDao);
	     assertFalse(isEmailSent);
	 }

	 @Test
	 void sendEmailForContactExceptionTest() {
	     boolean isEmailSent = contactUsService.sendEmailForContact(null);

	     verifyNoInteractions(emailService); 
	     assertFalse(isEmailSent);
	 }
}

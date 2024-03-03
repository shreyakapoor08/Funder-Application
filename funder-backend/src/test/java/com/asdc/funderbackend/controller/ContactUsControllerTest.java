package com.asdc.funderbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.asdc.funderbackend.entity.ContactUsDao;
import com.asdc.funderbackend.service.ContactUsService;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ContactUsControllerTest {

	@InjectMocks 
	ContactUsController contactUsController;
	
	@Mock
	ContactUsService contactUsService;
	

    @Test
    void sendEmailSuccessfulTest() {
    	 ContactUsDao contactUsDao = new ContactUsDao();
    	 contactUsDao.setName("BK");
    	 contactUsDao.setEmail("BK@gmail.com");
    	 contactUsDao.setMessage("Question 1");
    	 contactUsDao.setSubject("Regarding Doubt");
          
         when(contactUsService.sendEmailForContact(contactUsDao)).thenReturn(true);
         ResponseEntity<?> responseEntity = contactUsController.sendEmail(contactUsDao);

         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
         assertEquals(true, responseEntity.getBody());
    }

    @Test
    void sendEmailUnsuccessfulTest() {
    	ContactUsDao contactUsDao = new ContactUsDao();
	   	contactUsDao.setName("BK");
	   	contactUsDao.setEmail("BK@gmail.com");
	   	contactUsDao.setMessage("Question 1");
	   	contactUsDao.setSubject("Regarding Doubt");
        
        when(contactUsService.sendEmailForContact(contactUsDao)).thenReturn(false);
        ResponseEntity<?> responseEntity = contactUsController.sendEmail(contactUsDao);

        verify(contactUsService, times(1)).sendEmailForContact(contactUsDao);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(false, responseEntity.getBody());
    }
}

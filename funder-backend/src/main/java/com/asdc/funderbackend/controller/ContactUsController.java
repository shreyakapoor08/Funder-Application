package com.asdc.funderbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asdc.funderbackend.entity.ContactUsDao;
import com.asdc.funderbackend.entity.User;
import com.asdc.funderbackend.service.ContactUsService;

/**
 * The type Contact us controller.
 */
@RestController
@RequestMapping("/contact")
public class ContactUsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ContactUsService contactUsService;

	/**
	 * Send email response entity.
	 *
	 * @param contactUsDao the contact us dao
	 * @return the response entity
	 */
	@PostMapping("/sendEmail")
	public ResponseEntity<?> sendEmail(@RequestBody ContactUsDao contactUsDao){
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering into sendEmail(){}");
		}
		
		boolean isSent = contactUsService.sendEmailForContact(contactUsDao);
					
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Exit from sendEmail(){}");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(isSent);		
	}
}

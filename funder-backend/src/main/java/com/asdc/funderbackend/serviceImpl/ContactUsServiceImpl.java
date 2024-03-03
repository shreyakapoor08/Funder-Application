package com.asdc.funderbackend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asdc.funderbackend.entity.ContactUsDao;
import com.asdc.funderbackend.service.ContactUsService;

/**
 * The type Contact us service.
 */
@Service
public class ContactUsServiceImpl implements ContactUsService {

	@Autowired
	private EmailService emailService;
	
	@Override
	public boolean sendEmailForContact(ContactUsDao contactUsDao) {
		// email will be send to funder team with user queries.
		boolean isEmailSent = emailService.sendEmailForContactUs(contactUsDao);
		return isEmailSent;
	}
}

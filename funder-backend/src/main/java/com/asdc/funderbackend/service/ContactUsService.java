package com.asdc.funderbackend.service;

import com.asdc.funderbackend.entity.ContactUsDao;

/**
 * The interface Contact us service.
 */
public interface ContactUsService {
	/**
	 * Send email for contact boolean.
	 *
	 * @param contactUsDao the contact us dao
	 * @return the boolean
	 */
	public boolean sendEmailForContact(ContactUsDao contactUsDao);
}

package com.asdc.funderbackend.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.asdc.funderbackend.controller.UserController;
import com.asdc.funderbackend.entity.ContactUsDao;
import com.asdc.funderbackend.entity.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * The type Email service.
 */
@Service
public class EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Value("{spring.mail.username}")
	private String userName;
	
	@Autowired
    private JavaMailSender javaMailSender;

	/**
	 * Sets user name.
	 *
	 * @param userName the user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Send email.
	 *
	 * @param user  the user
	 * @param token the token
	 * @param page  the page
	 */
	public void sendEmail(User user, String token, String page) {
    	
    	if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering into sendEmail(){}");
		}
    	
    	if(page == "RESETPASSWORD") {
			// send email for reset-password.
	    	try {
	    		
	        	String subject ="Reset Password";
	        	String resetPasswordUrl = "http://129.173.67.169:3000/reset-password?token="+token;
	        	
	        	String greeting = "Dear " + user.getFirstName() + ",<br><br>";
	        	String resetInstructionPart1 = "We have received a request to reset your password. If you initiated this request, ";
	        	String resetInstructionPart2 = "please click on the link below to reset your password:<br><br>";

	        	String resetInstruction = resetInstructionPart1 + resetInstructionPart2;
	        	String resetLink = resetPasswordUrl + "<br><br>";
	        	String ignoreMessage = "If you did not request this, please ignore this email.<br><br>";
	        	String closing = "Thanks for working with us, <br>" +
	        	                 "Funder.com";
	        	
	            MimeMessage message = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setFrom(userName);
	            helper.setTo(user.getUserName());
	            helper.setSubject(subject);
	           
				// body of the email
			    String body = greeting +
	                    resetInstruction +
	                    resetLink +
	                    ignoreMessage +
	                    closing;
	            helper.setText(body, true);
	            
	            javaMailSender.send(message);            
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
    	}
    	if(page == "SIGNUP") {
			// send email for successful signup.
    		try {	        	
	        	String greeting = "Dear " + user.getFirstName() + ",<br><br>";
	        	String registrationMessage = "Thank you for registering on our website. We are excited to have you on board.<br>";
	        	String assistanceMessage = "Please let us know if you have any questions or need assistance.<br><br>";
	        	String closing = "Best regards,<br>" +
	        	                 "Your Website Team";
	        	
	        	String subject ="Registration Successful";
	            MimeMessage message = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setFrom(userName);
	            helper.setTo(user.getUserName());
	            helper.setSubject(subject);

				// body of the email
	            String body = greeting +
	                    registrationMessage +
	                    assistanceMessage +
	                    closing;           
	            helper.setText(body, true);
	            
	            javaMailSender.send(message);            
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
    		
    		if(LOGGER.isInfoEnabled()) {
    			LOGGER.info("Exit from login(){}");
    		}
    	}
    }

	/**
	 * Send email for contact us boolean.
	 *
	 * @param contactUsDao the contact us dao
	 * @return the boolean
	 */
	public boolean sendEmailForContactUs(ContactUsDao contactUsDao) {
		// email will be send to funder team with client queries.
    	try {
        	String greeting = "Dear " + "funder team" + ",<br><br>";
        	String emailInfo = "You have email from " + contactUsDao.getEmail() + ",<br><br>";
        	String messageContent = contactUsDao.getMessage() + "<br><br>";
        	String closing = "Best regards,<br>" +
        	                 contactUsDao.getName();
        	
        	String subject = contactUsDao.getSubject();
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(userName);
            helper.setTo("suport.funder@gmail.com");
            helper.setSubject(subject);
            String body = greeting +
                    emailInfo +
                    messageContent +
                    closing;	            
            helper.setText(body, true);
            javaMailSender.send(message);            
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
		return true;
    }
}

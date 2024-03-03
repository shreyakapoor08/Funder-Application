package com.asdc.funderbackend.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.asdc.funderbackend.entity.PaymentDao;
import com.asdc.funderbackend.service.PaymentService;

/**
 * The type Payment controller.
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private PaymentService paymentService;

	/**
	 * Handle razorpay request response entity.
	 *
	 * @param paymentDao the payment dao
	 * @return the response entity
	 */
	@PostMapping("/razorpay")
    public ResponseEntity<Object> handleRazorpayRequest(@RequestBody PaymentDao paymentDao) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering into handleRazorpayRequest(){}");
		}
		
        HashMap<Object, Object> jsonResponse = new HashMap<>();
        jsonResponse = paymentService.handlePayment(paymentDao);
        
        if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Exit from handleRazorpayRequest(){}");
		}
		
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }

}

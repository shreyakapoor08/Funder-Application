package com.asdc.funderbackend.service;

import java.util.HashMap;
import com.asdc.funderbackend.entity.PaymentDao;

/**
 * The interface Payment service.
 */
public interface PaymentService {

	/**
	 * Handle payment hash map.
	 *
	 * @param paymentDao the payment dao
	 * @return the hash map
	 */
	public HashMap<Object, Object> handlePayment(PaymentDao paymentDao);
}

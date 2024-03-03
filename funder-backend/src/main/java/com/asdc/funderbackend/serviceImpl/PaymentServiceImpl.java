package com.asdc.funderbackend.serviceImpl;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import com.asdc.funderbackend.entity.PaymentDao;
import com.asdc.funderbackend.repository.PaymentRepo;
import com.asdc.funderbackend.service.PaymentService;

/**
 * The type Payment service.
 */
@Service
public class PaymentServiceImpl implements PaymentService{


    private final String razorpayKeyId = "rzp_test_YVJU3eyNoJTnWC"; 
    private final String razorpayKeySecret = "96Psirw44nAZ4SXNlLFhA4Tn"; 
    private final int hundred = 100;

	/**
	 * The Payment repo.
	 */
	@Autowired PaymentRepo paymentRepo;
    
	@Override
	public HashMap<Object, Object> handlePayment(PaymentDao paymentDao) {
		RazorpayClient razorpay;
		HashMap<Object, Object> jsonResponse = new HashMap<>();
		try {
			// Creating Razorpayclient object to connect with razorpay.
			razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
			int paymentCapture = 1;
	        JSONObject options = new JSONObject();
	        options.put("amount", paymentDao.getAmount() * hundred);
	        options.put("currency", paymentDao.getCurrency());
	        options.put("receipt", java.util.UUID.randomUUID().toString());
	        options.put("payment_capture", paymentCapture);

			// Creating Order into razopay order dashboard.
	        Order response = razorpay.orders.create(options);
	   
	        jsonResponse.put("id", response.get("id"));
	        jsonResponse.put("currency", response.get("currency"));
	        jsonResponse.put("amount", response.get("amount"));
		       
		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		return jsonResponse;   
	}
}

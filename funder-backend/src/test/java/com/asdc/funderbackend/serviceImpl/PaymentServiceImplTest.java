package com.asdc.funderbackend.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.HashMap;
import com.razorpay.Order;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.asdc.funderbackend.entity.PaymentDao;
import com.razorpay.RazorpayClient;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class PaymentServiceImplTest {


    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private RazorpayClient razorpayClient;

    @Mock
    private Order razorpayOrder;

    private int amount = 100;
    @Test
    void testHandlePaymentSuccess() {
        RazorpayClient razorpayClientMock = mock(RazorpayClient.class);
        PaymentDao paymentDao = new PaymentDao();
        paymentDao.setAmount(amount);
        paymentDao.setCurrency("USD");

        JSONObject options = mock(JSONObject.class);

        // Creating the actual object
        HashMap<Object, Object> result = paymentService.handlePayment(paymentDao);

        //Checking the actual object is not empty means returning the result properly or not.
        assertEquals(result.isEmpty(),false);

    }

        @Test
        void testhandlePaymentFailure(){
            RazorpayClient razorpayClientMock = mock(RazorpayClient.class);

            PaymentDao paymentDao = new PaymentDao();
            paymentDao.setAmount(amount);
            paymentDao.setCurrency(null);

            JSONObject options = mock(JSONObject.class);

            // Creating the actual object
            HashMap<Object, Object> result = paymentService.handlePayment(paymentDao);

            assertEquals(!result.isEmpty(), false);
        }





}

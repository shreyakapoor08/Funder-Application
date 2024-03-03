package com.asdc.funderbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.HashMap;
import com.asdc.funderbackend.serviceImpl.PaymentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.asdc.funderbackend.entity.PaymentDao;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class PaymentControllerTest {

	@InjectMocks
	PaymentController paymentController;
	
	@Mock
    PaymentServiceImpl paymentService;

	@Test
	void handleRazorpayRequestSuccessTest() throws JsonProcessingException {
		HashMap<Object, Object> mockResponse = new HashMap<>();
		mockResponse.put("status", "success");

		when(paymentService.handlePayment(any(PaymentDao.class))).thenReturn(mockResponse);

		ResponseEntity<Object> responseEntity = paymentController.handleRazorpayRequest(new PaymentDao());

		verifyNoMoreInteractions(paymentService);

		ObjectMapper objectMapper = new ObjectMapper();
		String expectedResponseBody = objectMapper.writeValueAsString(mockResponse);
		String actualResponseBody = responseEntity.getBody().toString();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void handleRazorpayRequestEmptyResponseTest() throws Exception {
		HashMap<Object, Object> mockResponse = new HashMap<>();

		when(paymentService.handlePayment(any(PaymentDao.class))).thenReturn(mockResponse);

		PaymentDao paymentDao = new PaymentDao();
		paymentDao.setAmount(anyInt());
		paymentDao.setCurrency("CAD");
		ResponseEntity<Object> responseEntity = paymentController.handleRazorpayRequest(paymentDao);

		verify(paymentService).handlePayment(any(PaymentDao.class));

		assertEquals("{}", responseEntity.getBody().toString());
	}
	
	
}

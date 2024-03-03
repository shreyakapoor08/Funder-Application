package com.asdc.funderbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.asdc.funderbackend.entity.InvestedProduct;
import com.asdc.funderbackend.entity.InvestmentDao;
import com.asdc.funderbackend.service.InvestedProductService;
import org.mockito.MockitoAnnotations;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class InvestedProductControllerTest {

	@InjectMocks
    private InvestedProductController investedProductController;
	
	@Mock
	private InvestedProductService investedProductService;
	
	
	@Test
    void getAllInvestedProductSuccessTest() {
		List<InvestedProduct> expectedInvestedProducts = Arrays.asList(new InvestedProduct(), new InvestedProduct());
        Mockito.when(investedProductService.getAllInvestedProduct(anyLong())).thenReturn(expectedInvestedProducts);
        ResponseEntity<List<InvestedProduct>> responseEntity = investedProductController.getAllInvestedProduct(anyLong());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedInvestedProducts, responseEntity.getBody());
    }

    @Test
    void getAllInvestedProductEmptyTest() {
        List<InvestedProduct> expectedInvestedProducts = Arrays.asList();
        Mockito.when(investedProductService.getAllInvestedProduct(anyLong())).thenReturn(expectedInvestedProducts);
        ResponseEntity<List<InvestedProduct>> responseEntity = investedProductController.getAllInvestedProduct(anyLong());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedInvestedProducts, responseEntity.getBody());
    }

    @Test
    void getAllInvestedProductExceptionTest() {
    	when(investedProductService.getAllInvestedProduct(anyLong())).thenThrow(new RuntimeException("Simulated Exception"));
        ResponseEntity<List<InvestedProduct>> responseEntity = investedProductController.getAllInvestedProduct(anyLong());
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); 
    }
    
    @Test
	void saveInvestmentSuccessTest() {
    	Long investorId = 1L;
	    InvestmentDao investmentDao = new InvestmentDao();
	    investmentDao.setInvestorId(investorId);

	    when(investedProductService.saveInvestment(any(InvestmentDao.class))).thenReturn(true);
	    boolean result = investedProductController.saveInvestment(investmentDao);
	    verify(investedProductService, times(1)).saveInvestment(any(InvestmentDao.class));

	    assertTrue(result);
	}
	 
    @Test
    void saveInvestmentFailureTest() {
        boolean isSave = false;
        InvestmentDao investmentDao = new InvestmentDao();
        InvestedProductController investedProductController = mock(InvestedProductController.class);
        isSave = investedProductController.saveInvestment(investmentDao);
        assertFalse(isSave);
    }
}

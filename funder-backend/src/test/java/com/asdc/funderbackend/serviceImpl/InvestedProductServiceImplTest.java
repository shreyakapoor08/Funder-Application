package com.asdc.funderbackend.serviceImpl;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.asdc.funderbackend.entity.InvestedProduct;
import com.asdc.funderbackend.entity.InvestmentDao;
import com.asdc.funderbackend.repository.InvestedProductRepo;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class InvestedProductServiceImplTest {

    @Mock
    private InvestedProductRepo investedProductRepo;

    @InjectMocks
    private InvestedProductServiceImpl investedProductServiceImpl;

    @Test
    void getAllInvestedProductTest() {
        Long userId = 1L;

        List<InvestedProduct> expectedInvestedProducts = Arrays.asList(new InvestedProduct(), new InvestedProduct());
        Mockito.when(investedProductRepo.getInvestedProductByUserId(anyLong())).thenReturn(expectedInvestedProducts);
        List<InvestedProduct> actualInvestedProducts = investedProductServiceImpl.getAllInvestedProduct(userId);

        assertEquals(expectedInvestedProducts, actualInvestedProducts);
    }


    @Test
    void getAllInvestedProductEmptyTest() {
        Long userId = 1L;

        List<InvestedProduct> expectedInvestedProducts = Arrays.asList(new InvestedProduct(), new InvestedProduct());
        Mockito.when(investedProductRepo.getInvestedProductByUserId(anyLong())).thenReturn(expectedInvestedProducts);
        List<InvestedProduct> actualInvestedProducts = investedProductServiceImpl.getAllInvestedProduct(userId);

        assertEquals(expectedInvestedProducts, actualInvestedProducts);
    }

    @Test
    void getAllInvestedProductWithNullTest() {
        Long userId = 1L;

        Mockito.when(investedProductRepo.getInvestedProductByUserId(anyLong())).thenReturn(null);
        List<InvestedProduct> actualInvestedProducts = investedProductServiceImpl.getAllInvestedProduct(userId);

        assertEquals(null, actualInvestedProducts);
    }

    @Test
    void saveInvestmentFailure() {
        InvestmentDao mockInvestmentDao = new InvestmentDao();
        mockInvestmentDao.setPaymentStatus(true);

        boolean result = investedProductServiceImpl.saveInvestment(mockInvestmentDao);

        assertFalse(result, "");
    }
}


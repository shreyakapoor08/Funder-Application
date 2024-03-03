package com.asdc.funderbackend.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.asdc.funderbackend.entity.Product;
import com.asdc.funderbackend.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("qa")
public class ProductControllerIntegrationTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllProductsTest() throws Exception {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setTitle("First Try");
        productList.add(product1);

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/products/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(product1.getTitle()));
    }

    @Test
    public void getProductByProductIdTest() throws Exception {
        Long productId = 2L;
        Product product = new Product();
        product.setId(productId);
        product.setTitle("Long Desc Try");

        when(productService.getProductByProductId(productId)).thenReturn(product);

        mockMvc.perform(get("/products/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.title").value(product.getTitle()));
    }

}

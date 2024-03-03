package com.asdc.funderbackend.controller;

import com.asdc.funderbackend.entity.Product;
import com.asdc.funderbackend.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private static final Long SAMPLE_PRODUCT_ID = 123L;


    @Test
    void getAllProductsSuccessTest() {
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());
        when(productService.getAllProducts()).thenReturn(expectedProducts);

        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedProducts, responseEntity.getBody());
    }

    @Test
    void getAllProductsEmptyTest() {
        List<Product> expectedProducts = Arrays.asList();
        when(productService.getAllProducts()).thenReturn(expectedProducts);

        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(expectedProducts, responseEntity.getBody());
    }

    @Test
    void getAllProductsExceptionTest() {
        when(productService.getAllProducts()).thenThrow(new RuntimeException("Simulated Exception"));

        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void getProductsByUserIdSuccessTest() {
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());
        when(productService.getProductsByUserId(any(Long.class))).thenReturn(expectedProducts);

        ResponseEntity<List<Product>> responseEntity = productController.getProductsByUserId(anyLong());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedProducts, responseEntity.getBody());
    }

    @Test
    void getProductsByUserIdEmptyTest() {

        List<Product> expectedProducts = new ArrayList<>();
        when(productService.getProductsByUserId(any(Long.class))).thenReturn(expectedProducts);

        ResponseEntity<List<Product>> responseEntity = productController.getProductsByUserId(anyLong());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(expectedProducts, responseEntity.getBody());
    }

    @Test
    void getProductsByUserIdExceptionTest() {
        when(productService.getProductsByUserId(any(Long.class))).thenThrow(new RuntimeException("Simulated Exception"));

        ResponseEntity<List<Product>> responseEntity = productController.getProductsByUserId(anyLong());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void addProductSuccessTest() throws JsonMappingException, JsonProcessingException, IOException {
    	String a = "ACV";
        MultipartFile mockImage = Mockito.mock(MultipartFile.class);
        
        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        when(productService.addProduct(any(String.class), any(MultipartFile.class))).thenReturn(expectedProduct);
        ResponseEntity<Product> responseEntity = productController.addProduct(a, mockImage);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedProduct, responseEntity.getBody());
    }

    @Test
    void addProductFailureTest() throws JsonMappingException, JsonProcessingException, IOException {
    	String a = "ACV";
        when(productService.addProduct(any(String.class), any(MultipartFile.class))).thenReturn(null);

        ResponseEntity<Product> responseEntity = productController.addProduct(a, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void addProductNullImageFailureTest() throws JsonMappingException, JsonProcessingException, IOException {
        String a = "ACV";
        MultipartFile mockImage = Mockito.mock(MultipartFile.class);

        when(productService.addProduct(any(String.class), any(MultipartFile.class))).thenReturn(null);

        ResponseEntity<Product> responseEntity = productController.addProduct(a, mockImage);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void getProductByProductIdSuccessTest() {
        Long productId = SAMPLE_PRODUCT_ID;

        Product expectedProduct = new Product();
        expectedProduct.setId(productId);

        when(productService.getProductByProductId(any(Long.class))).thenReturn(expectedProduct);

        ResponseEntity<Product> responseEntity = productController.getProductByProductId(anyLong());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedProduct, responseEntity.getBody());
    }

    @Test
    void getProductByProductIdEmptyTest() {

        when(productService.getProductByProductId(any(Long.class))).thenReturn(null);
        ResponseEntity<Product> responseEntity = productController.getProductByProductId(anyLong());

        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void getProductByProductIdExceptionTest() {
        when(productService.getProductByProductId(any(Long.class))).thenThrow(new RuntimeException("Simulated Exception"));
        ResponseEntity<Product> responseEntity = productController.getProductByProductId(anyLong());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void deleteProductSuccessTest() {
        Long productId = 1L;
        doNothing().when(productService).deleteProduct(productId);

        ResponseEntity<String> responseEntity = productController.deleteProduct(productId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Product with ID: " + productId + " deleted successfully.", responseEntity.getBody());
    }

    @Test
    void deleteProductFailureTest() {
        Long productId = 1L;
        doThrow(new RuntimeException("Simulated Exception")).when(productService).deleteProduct(productId);

        ResponseEntity<String> responseEntity = productController.deleteProduct(productId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error deleting the product with ID: " + productId, responseEntity.getBody());
    }

    @Test
    void deleteProductExceptionTest() {
        Long productId = 1L;
        doThrow(new RuntimeException("Simulated Exception"))
                .when(productService)
                .deleteProduct(productId);

        ResponseEntity<String> responseEntity = productController.deleteProduct(productId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error deleting the product with ID: " + productId, responseEntity.getBody());
    }

    @Test
    public void updateProductSuccessTest() throws IOException {
        Long productId = 1L;
        String updatedProductJson = "{\"title\":\"Updated Title\",\"description\":\"Updated Description\"}";
        MultipartFile image = mock(MultipartFile.class);

        Product expectedUpdatedProduct = new Product();
        when(productService.updateProduct(eq(productId), anyString())).thenReturn(expectedUpdatedProduct);
        ResponseEntity<Product> responseEntity = productController.updateProduct(productId, updatedProductJson);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedUpdatedProduct, responseEntity.getBody());       
    }

    @Test
    void updateProductFailureTest() throws IOException {
        Long productId = 1L;
        String updatedProductJson = "{\"title\":\"Updated Title\",\"description\":\"Updated Description\"}";
        MultipartFile image = mock(MultipartFile.class);
        
        when(productService.updateProduct(eq(productId), anyString())).thenReturn(null);

        ResponseEntity<Product> responseEntity = productController.updateProduct(productId, updatedProductJson);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void updateProductExceptionTest() throws IOException {
        Long productId = 1L;
        String updatedProductJson = "{\"title\":\"Updated Title\",\"description\":\"Updated Description\"}";
        MultipartFile image = mock(MultipartFile.class);

        when(productService.updateProduct(eq(productId), anyString())).thenThrow(new RuntimeException("Something went wrong"));
        ResponseEntity<Product> responseEntity = productController.updateProduct(productId, updatedProductJson);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }
}

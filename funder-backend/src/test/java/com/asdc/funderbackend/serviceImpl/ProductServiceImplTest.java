package com.asdc.funderbackend.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.asdc.funderbackend.entity.Product;
import com.asdc.funderbackend.repository.ProductRepository;
import com.asdc.funderbackend.service.FirebaseFileStorageService;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private FirebaseFileStorageService fileStorageService;

    @InjectMocks
    private ProductServiceImpl productService;



    @Test
    void getAllProductsTest() {
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());
        Mockito.when(productRepository.findAll()).thenReturn(expectedProducts);
        List<Product> actualProducts = productService.getAllProducts();

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void getAllProductsEmptyTest() {
        List<Product> expectedProducts = new ArrayList<>();
        Mockito.when(productRepository.findAll()).thenReturn(expectedProducts);
        List<Product> actualProducts = productService.getAllProducts();

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void getAllProductsWithNullTest() {
        Mockito.when(productRepository.findAll()).thenReturn(null);
        List<Product> actualProducts = productService.getAllProducts();

        assertNull(actualProducts);
    }

    @Test
    void getProductsByUserIdTest() {

        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());
        when(productRepository.findByUserId(anyLong())).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getProductsByUserId(anyLong());

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void getProductsByUserIdEmptyTest() {

        List<Product> expectedProducts = new ArrayList<>();
        when(productRepository.findByUserId(anyLong())).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getProductsByUserId(anyLong());

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void getProductsByUserIdWithNullTest() {

        when(productRepository.findByUserId(anyLong())).thenReturn(null);

        List<Product> actualProducts = productService.getProductsByUserId(anyLong());

        assertNull(actualProducts);
    }

    @Test
    void addProductTest() throws IOException {
        String productJson = "{\"title\":\"Test Product\"}";
        MultipartFile mockImage = mock(MultipartFile.class);
        Product productToAdd = new Product();
        productToAdd.setTitle("Test Product");
        lenient().when(objectMapper.readValue(eq(productJson), eq(Product.class))).thenReturn(productToAdd);

        lenient().when(fileStorageService.uploadFile(mockImage)).thenReturn("mockImageURL");

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(productToAdd);

        Product addedProduct = productService.addProduct(productJson, mockImage);

        assertEquals(productToAdd, addedProduct);
    }

    void addProductWithFileUploadFailureTest() throws IOException {
        Product product = new Product();
        String productJson = "{\"title\":\"Test Product\"}"; // Replace with your actual JSON string
        MultipartFile mockImage = mock(MultipartFile.class);

//        doThrow(new IOException("Simulated IOException")).when(fileStorageService).uploadFile(any());

        IOException exception = assertThrows(IOException.class, () -> productService.addProduct(productJson, mockImage));

        assertEquals("Simulated IOException", exception.getMessage());

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void getProductsByProductIdSuccessTest() {

        Product expectedProduct = new Product();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(expectedProduct));


        Product actualProduct = productService.getProductByProductId(anyLong());

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void getProductByProductIdEmptyTest() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            productService.getProductByProductId(anyLong());
        } catch (RuntimeException e) {
            assertEquals("Product not found with id: 0", e.getMessage());
        }
    }

    @Test
    void updateProductSuccessTest() throws IOException {
        Long productId = 1L;

        String productJson = "{"
                + "\"title\":\"Updated Title\","
                + "\"description\":\"Updated Description\""
                + "}";

        Product existingProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(existingProduct);

        Product result = productService.updateProduct(productId, productJson);

        verify(productRepository).findById(productId);
        verify(productRepository).save(existingProduct);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
    }


    @Test
    void updateProductFailureTest() throws IOException {
        Long productId = 1L;

        String productJson = "{"
                + "\"title\":\"Updated Title\","
                + "\"description\":\"Updated Description\""
                + "}";

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(productId, productJson);
        });

        verify(productRepository).findById(productId);
    }


    @Test
    void deleteProductFailureTest() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        try {
            productService.deleteProduct(productId);
        } catch (Exception e) {
            assertEquals("Product not found with id: " + productId, e.getMessage());
        }
    }

    @Test
    void testGetProductById() {
        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setTitle("Test Product");

        when(productRepository.getProductById(productId)).thenReturn(mockProduct);
        Product result = productService.getProductById(productId);
        verify(productRepository).getProductById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Test Product", result.getTitle());
    }

    @Test
    @Transactional
    void testUpdateAmountRaised() {

        when(productRepository.updateAmountRaised(anyLong(), anyLong())).thenReturn(1);
        int result = productService.updateAmountRaised(anyLong(), anyLong());
        verify(productRepository).updateAmountRaised(anyLong(), anyLong());

        assertEquals(1, result);
    }
}

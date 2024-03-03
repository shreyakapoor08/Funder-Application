package com.asdc.funderbackend.service;

import com.asdc.funderbackend.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for managing product-related operations.
 */
public interface ProductService {
    /**
     * Retrieve all products.
     *
     * @return List of products.
     */
    List<Product> getAllProducts();

    /**
     * Add a new product.
     *
     * @param product The product details.
     * @param image   The product image.
     * @return The added product.
     * @throws JsonMappingException    If there is an issue mapping JSON.
     * @throws JsonProcessingException If there is an issue processing JSON.
     * @throws IOException             If an I/O exception occurs.
     */
    Product addProduct(String product, MultipartFile image) throws JsonMappingException, JsonProcessingException, IOException;

    /**
     * Retrieve a product by its ID.
     *
     * @param id The ID of the product.
     * @return The product with the specified ID.
     */
    Product getProductById(Long id);

    /**
     * Update the amount raised for a product.
     *
     * @param amountRaised The new amount raised.
     * @param productId    The ID of the product to be updated.
     * @return The updated amount raised.
     */
    int updateAmountRaised(Long amountRaised, Long productId);

    /**
     * Retrieve products associated with a specific user.
     *
     * @param userId The ID of the user.
     * @return List of products associated with the specified user.
     */
    List<Product> getProductsByUserId(Long userId);

    /**
     * Retrieve a product by its ID.
     *
     * @param productId The ID of the product.
     * @return The product with the specified ID.
     */
    Product getProductByProductId(Long productId);

    /**
     * Update an existing product.
     *
     * @param productId      The ID of the product to be updated.
     * @param updatedProduct The updated product details.
     * @return The updated product.
     * @throws JsonMappingException    If there is an issue mapping JSON.
     * @throws JsonProcessingException If there is an issue processing JSON.
     * @throws IOException             If an I/O exception occurs.
     */
    Product updateProduct(Long productId, String updatedProduct) throws JsonMappingException, JsonProcessingException, IOException;

    /**
     * Delete a product by its ID.
     *
     * @param productId The ID of the product to be deleted.
     */
    void deleteProduct(Long productId);
}

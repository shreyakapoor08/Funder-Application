package com.asdc.funderbackend.serviceImpl;

import com.asdc.funderbackend.entity.Product;
import com.asdc.funderbackend.repository.ProductRepository;
import com.asdc.funderbackend.service.FirebaseFileStorageService;
import com.asdc.funderbackend.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of the ProductService interface for managing product-related operations.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    
    FirebaseFileStorageService firebaseFilStorageService;
    
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, FirebaseFileStorageService firebaseFilStorageService) {
        this.productRepository = productRepository;
        this.firebaseFilStorageService = firebaseFilStorageService;
    }

    /**
     * Retrieve all products.
     *
     * @return List of products.
     */
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

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
    @Override
    public Product addProduct(String product, MultipartFile image) throws JsonMappingException, JsonProcessingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Product productObj = objectMapper.readValue(product, Product.class);

        productObj.setProductImage("https://firebasestorage.googleapis.com/v0/b/funder-asdc.appspot.com/o/MicrosoftTeams-image%20(1).png?alt=media&token=57b1d577-bf4e-4911-8ee4-12bd288a8ecf");        
        return productRepository.save(productObj);
    }

    /**
     * Retrieve a product by its ID.
     *
     * @param id The ID of the product.
     * @return The product with the specified ID.
     */
    @Override
	public Product getProductById(Long id) {
		return productRepository.getProductById(id);
	}

    /**
     * Update the amount raised for a product.
     *
     * @param amountRaised The new amount raised.
     * @param productId    The ID of the product to be updated.
     * @return The updated amount raised.
     */
	@Override
	@Transactional
	public int updateAmountRaised(Long amountRaised, Long productId) {
		return productRepository.updateAmountRaised(amountRaised, productId);
	}

    /**
     * Retrieve products associated with a specific user.
     *
     * @param userId The ID of the user.
     * @return List of products associated with the specified user.
     */
    @Override
    public List<Product> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }

    /**
     * Retrieve a product by its ID.
     *
     * @param productId The ID of the product.
     * @return The product with the specified ID.
     * @throws RuntimeException If the product with the given ID is not found.
     */
    @Override
    public Product getProductByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> productNotFoundException(productId));
    }

    private RuntimeException productNotFoundException(Long productId) {
        return new RuntimeException("Product not found with id: " + productId);
    }

    /**
     * Update an existing product.
     *
     * @param productId The ID of the product to be updated.
     * @param product   The updated product details.
     * @return The updated product.
     * @throws IOException If an I/O exception occurs.
     */
    @Override
    public Product updateProduct(Long productId, String product) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Product updatedProduct = objectMapper.readValue(product, Product.class);

        Product existingProduct = getProductByIdOrThrow(productId);

        updateProductFields(existingProduct, updatedProduct);

        return productRepository.save(existingProduct);
    }

    private Product getProductByIdOrThrow(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> productNotFoundException(productId));
    }


    private void updateProductFields(Product existingProduct, Product updatedProduct) {
        existingProduct.setTitle(updatedProduct.getTitle());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setDateOfRoundClosing(updatedProduct.getDateOfRoundClosing());
        existingProduct.setFundingAmount(updatedProduct.getFundingAmount());
        existingProduct.setPercentage(updatedProduct.getPercentage());
    }

    /**
     * Delete a product by its ID.
     *
     * @param productId The ID of the product to be deleted.
     * @throws RuntimeException If the product with the given ID is not found.
     */
    @Override
    public void deleteProduct(Long productId) {
        productRepository.findById(productId).ifPresentOrElse(
                product -> productRepository.delete(product),
                () -> {
                    throw new RuntimeException("Product not found with id: " + productId);
                }
        );
    }
}
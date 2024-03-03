package com.asdc.funderbackend.controller;

import com.asdc.funderbackend.entity.Product;
import com.asdc.funderbackend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller class for managing product-related operations.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieve all products.
     *
     * @return ResponseEntity containing a list of products and HTTP status.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Entering into getAllProducts(){}");
        }

        List<Product> foundedProducts = null;
        HttpStatus status = HttpStatus.OK;

        try {
            foundedProducts = productService.getAllProducts();
            if (foundedProducts.isEmpty()) {
                status = HttpStatus.NO_CONTENT;
                return ResponseEntity.status(status).body(foundedProducts);
            }
            return ResponseEntity.status(status).body(foundedProducts);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOGGER.error("Error while fetching records: {}", ex);
            return ResponseEntity.status(status).body(null);
        } finally {
            if(LOGGER.isInfoEnabled()) {
                LOGGER.info("Exit from getAllProducts(){}");
            }
        }
    }

    /**
     * Retrieve products associated with a specific user.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity containing a list of products and HTTP status.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Product>> getProductsByUserId(@PathVariable Long userId) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Entering into getProductsByUserId(){}");
        }

        List<Product> userProducts = null;
        HttpStatus status = HttpStatus.OK;

        try {
            userProducts = productService.getProductsByUserId(userId);
            if (userProducts.isEmpty()) {
                status = HttpStatus.NO_CONTENT;
                return ResponseEntity.status(status).body(userProducts);
            }
            return ResponseEntity.status(status).body(userProducts);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOGGER.error("Error while fetching products by user ID: {}", ex);
            return ResponseEntity.status(status).body(null);
        } finally {
            if(LOGGER.isInfoEnabled()) {
                LOGGER.info("Exit from getProductsByUserId(){}");
            }
        }
    }

    /**
     * Add a new product.
     *
     * @param product The product details.
     * @param image   The product image.
     * @return ResponseEntity containing the added product and HTTP status.
     */
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestPart("product") String product, @RequestParam("image") MultipartFile image) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Entering into addProduct(){}");
        }

        HttpStatus status = HttpStatus.OK;

        try {
            Product addedProduct = productService.addProduct(product, image);
            if (addedProduct == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                LOGGER.error("Error adding the product. Returned product is null.");
                return ResponseEntity.status(status).body(null);
            }
            return ResponseEntity.status(status).body(addedProduct);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOGGER.error("Error while adding a product: {}", ex);
            return ResponseEntity.status(status).body(null);
        } finally {
            if(LOGGER.isInfoEnabled()) {
                LOGGER.info("Exit from addProduct(){}");
            }
        }
    }

    /**
     * Retrieve a product by its ID.
     *
     * @param productId The ID of the product.
     * @return ResponseEntity containing the product and HTTP status.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductByProductId(@PathVariable Long productId) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Entering into getProductByProductId(){}");
        }

        HttpStatus status = HttpStatus.OK;

        try {
            Product product = productService.getProductByProductId(productId);
            return ResponseEntity.status(status).body(product);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOGGER.error("Error while fetching product by ID: {}", ex);
            return ResponseEntity.status(status).body(null);
        } finally {
            if(LOGGER.isInfoEnabled()) {
                LOGGER.info("Exit from getProductByProductId(){}");
            }
        }
    }

    /**
     * Update an existing product.
     *
     * @param productId      The ID of the product to be updated.
     * @param updatedProduct The updated product details.
     * @return ResponseEntity containing the updated product and HTTP status.
     */
    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestPart("updatedProduct") String updatedProduct) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Entering into updateProduct(){}");
        }

        HttpStatus status = HttpStatus.OK;

        try {
            Product updated = productService.updateProduct(productId, updatedProduct);
            if (updated == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                LOGGER.error("Error updating the product. Returned product is null.");
                return ResponseEntity.status(status).body(null);
            }
            return ResponseEntity.status(status).body(updated);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOGGER.error("Error while updating the product: {}", ex);
            return ResponseEntity.status(status).body(null);
        } finally {
            if(LOGGER.isInfoEnabled()) {
                LOGGER.info("Exit from updateProduct(){}");
            }
        }
    }

    /**
     * Delete a product by its ID.
     *
     * @param productId The ID of the product to be deleted.
     * @return ResponseEntity containing a success message and HTTP status.
     */
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Entering into deleteProduct(){}");
        }

        HttpStatus status = HttpStatus.OK;

        try {
            productService.deleteProduct(productId);
            return ResponseEntity.status(status).body("Product with ID: " + productId + " deleted successfully.");
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOGGER.error("Error while deleting the product: {}", ex);
            return ResponseEntity.status(status).body("Error deleting the product with ID: " + productId);
        } finally {
            if(LOGGER.isInfoEnabled()) {
                LOGGER.info("Exit from deleteProduct(){}");
            }
        }
    }
}

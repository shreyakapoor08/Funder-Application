package com.asdc.funderbackend.repository;

import com.asdc.funderbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing product-related database operations.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * Retrieve a product by its ID using a native SQL query.
	 *
	 * @param id The ID of the product.
	 * @return The product with the specified ID.
	 */
	@Query(value = "select * from product where id =:id", nativeQuery = true)
	Product getProductById(@Param("id") Long id);

	/**
	 * Update the amount raised for a product using a native SQL query.
	 *
	 * @param amountRaised The new amount raised.
	 * @param productId    The ID of the product to be updated.
	 * @return The number of affected rows.
	 */
	@Modifying
	@Query(value = "UPDATE product SET raised_amount = :amountRaised WHERE id = :productId", nativeQuery = true)
	int updateAmountRaised(@Param("amountRaised") Long amountRaised, @Param("productId") Long productId);

	/**
	 * Retrieve products associated with a specific user.
	 *
	 * @param userId The ID of the user.
	 * @return List of products associated with the specified user.
	 */
	List<Product> findByUserId(Long userId);


}

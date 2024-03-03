package com.asdc.funderbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.asdc.funderbackend.entity.InvestedProduct;

/**
 * The interface Invested product repo.
 */
@Repository
public interface InvestedProductRepo extends JpaRepository<InvestedProduct, Long>{

	/**
	 * Gets invested product by user id.
	 *
	 * @param userId the user id
	 * @return the invested product by user id
	 */
	@Query(value = "select * from invested_product where investor_id =:userId", nativeQuery = true)
	List<InvestedProduct> getInvestedProductByUserId(@Param("userId") Long userId);
}

package com.asdc.funderbackend.service;

import java.util.List;

import com.asdc.funderbackend.entity.InvestedProduct;
import com.asdc.funderbackend.entity.InvestmentDao;

/**
 * The interface Invested product service.
 */
public interface InvestedProductService {

	/**
	 * Gets all invested product.
	 *
	 * @param userId the user id
	 * @return the all invested product
	 */
	List<InvestedProduct> getAllInvestedProduct(Long userId);

	/**
	 * Save investment boolean.
	 *
	 * @param investmentDao the investment dao
	 * @return the boolean
	 */
	boolean saveInvestment(InvestmentDao investmentDao);
}

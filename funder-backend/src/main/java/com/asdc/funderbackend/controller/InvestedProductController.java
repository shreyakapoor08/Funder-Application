package com.asdc.funderbackend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.asdc.funderbackend.entity.InvestedProduct;
import com.asdc.funderbackend.entity.InvestmentDao;
import com.asdc.funderbackend.service.InvestedProductService;

/**
 * The type Invested product controller.
 */
@RestController
@RequestMapping("/investedProduct")
public class InvestedProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private InvestedProductService investedProductService;

	/**
	 * Gets all invested product.
	 *
	 * @param userId the user id
	 * @return the all invested product
	 */
	@GetMapping("/getAllInvestedProduct")
	public ResponseEntity<List<InvestedProduct>> getAllInvestedProduct(@RequestParam("userId") Long userId){
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering into getAllInvestedProduct(){}");
		}
		List<InvestedProduct> investedProducts = null;
		try {
			investedProducts = investedProductService.getAllInvestedProduct(userId);
		}
		catch (Exception ex) {
			LOGGER.error("Error while fetching record(){}", ex);
		}
					
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Exit from getAllInvestedProduct(){}");
		}
		return ResponseEntity.status(HttpStatus.OK).body(investedProducts);		
	}

	/**
	 * Save investment boolean.
	 *
	 * @param investmentDao the investment dao
	 * @return the boolean
	 */
	@PostMapping("/saveInvestment")
	public boolean saveInvestment(@RequestBody InvestmentDao investmentDao){
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering into saveInvestment(){}");
		}
		
		boolean isSave = false;
		try {
			isSave = investedProductService.saveInvestment(investmentDao);
		} catch (Exception e) {
			return isSave;
		}
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Exit from saveInvestment(){}");
		}
		
		return isSave;
	}
}

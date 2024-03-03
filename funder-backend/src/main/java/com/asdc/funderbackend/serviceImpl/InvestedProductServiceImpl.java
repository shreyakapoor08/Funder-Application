package com.asdc.funderbackend.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.asdc.funderbackend.entity.InvestedProduct;
import com.asdc.funderbackend.entity.InvestmentDao;
import com.asdc.funderbackend.entity.Payment;
import com.asdc.funderbackend.entity.Product;
import com.asdc.funderbackend.repository.InvestedProductRepo;
import com.asdc.funderbackend.repository.PaymentRepo;
import com.asdc.funderbackend.service.InvestedProductService;

/**
 * The type Invested product service.
 */
@Service
public class InvestedProductServiceImpl implements InvestedProductService{

	/**
	 * The Invested product repo.
	 */
	@Autowired
	InvestedProductRepo investedProductRepo;

	/**
	 * The Product service.
	 */
	@Autowired
	ProductServiceImpl productServiceImpl;

	/**
	 * The Payment repo.
	 */
	@Autowired
	PaymentRepo paymentRepo;	
	
	@Override
	public List<InvestedProduct> getAllInvestedProduct(Long userId) {
			return	investedProductRepo.getInvestedProductByUserId(userId);
	}
	
	@Override
//	@Transactional(rollbackFor = Exception.class)
	public boolean saveInvestment(InvestmentDao investmentDao){
		// Once payment will be successfull investment details will be saved to table.
		try {
			if(investmentDao.isPaymentStatus()) {
				
				Product product = new Product();
				product.setId(investmentDao.getProductId());
				
				InvestedProduct investedProduct = new InvestedProduct();
				investedProduct.setInvestorId(investmentDao.getInvestorId());
				investedProduct.setMoneyInvested(investmentDao.getAmount());
				investedProduct.setProduct(product);
				
				Product product1 = productServiceImpl.getProductById(investmentDao.getProductId());
				
				// Calculating the percentage of equity that user will get based on amount
				Float numerator = (Float.parseFloat(Long.toString(investmentDao.getAmount())) *
						product1.getPercentage());
				Float denominator = (Float.parseFloat(Long.toString(product1.getFundingAmount())));
				Float percentageCount = numerator / denominator;
		
				investedProduct.setPercentageOwned(percentageCount);
				InvestedProduct investedProduct2 =  investedProductRepo.save(investedProduct);
				
				if(investmentDao != null) {
					Payment payment = new Payment();
					payment.setRazorPayPaymentId(investmentDao.getRazorPayPaymentId());
					payment.setAmount(investmentDao.getAmount());
					payment.setInvestedProductId(investedProduct2.getId());
					payment.setStatus(investmentDao.isPaymentStatus());
					paymentRepo.save(payment);
				}
				
				// updating amountRaised column in table.
				Long amountRaised = product1.getRaisedAmount() + investmentDao.getAmount();
				
				int isUpdated = productServiceImpl.updateAmountRaised(amountRaised, investmentDao.getProductId());
				if(isUpdated == 1) {
					return true;
				}else {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}


}

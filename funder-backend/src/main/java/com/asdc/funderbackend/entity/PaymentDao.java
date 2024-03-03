package com.asdc.funderbackend.entity;

public class PaymentDao {
	
	int amount;
	String currency;
	
	public PaymentDao() {}
	public PaymentDao(int i, String string) {
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}

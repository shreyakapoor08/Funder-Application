package com.asdc.funderbackend.entity;

import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "INVESTED_PRODUCT")
public class InvestedProduct {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "INVESTOR_ID")
    private Long investorId;
    
    @Column(name = "MONEY_INVESTED")
    private Long moneyInvested;
    
    @Column(name = "PERCENTAGE_OWNED")
    private Float percentageOwned;
    
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateOfInvestment;

    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updated_at;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvestorId() {
		return investorId;
	}

	public void setInvestorId(Long investorId) {
		this.investorId = investorId;
	}

	public Long getMoneyInvested() {
		return moneyInvested;
	}

	public void setMoneyInvested(Long moneyInvested) {
		this.moneyInvested = moneyInvested;
	}

	public Float getPercentageOwned() {
		return percentageOwned;
	}

	public void setPercentageOwned(Float percentageOwned) {
		this.percentageOwned = percentageOwned;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getDateOfInvestment() {
		return dateOfInvestment;
	}

	public void setDateOfInvestment(Date dateOfInvestment) {
		this.dateOfInvestment = dateOfInvestment;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
}

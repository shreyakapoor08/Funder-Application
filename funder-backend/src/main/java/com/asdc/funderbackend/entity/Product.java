package com.asdc.funderbackend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "category")
	private String category;

	@Column(name = "product_image")
	private String productImage;

	@Column(name = "date_of_round_closing")
	private Date dateOfRoundClosing;

	@Column(name = "funding_amount")
	private Long fundingAmount;

	@Column(name = "raised_amount")
	private Long raisedAmount = 0L;

	@Column(name = "percentage")
	private Float percentage;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date updatedAt;

	@ManyToOne
	@JoinColumn(name = "founder_id")
	private User user;

	@Column(name = "is_active")
	private boolean isActive;

	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	// Method to update isActive based on dateOfRoundClosing
	private boolean isRoundClosed() {
		Date currentDate = new Date();
		return dateOfRoundClosing != null && currentDate.after(dateOfRoundClosing);
	}

	private boolean isAmountExceeded() {
		return raisedAmount != null && fundingAmount != null && raisedAmount > fundingAmount;
	}

	@PrePersist
	@PreUpdate
	public void updateIsActive() {
		isActive = !(isRoundClosed() || isAmountExceeded());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public Date getDateOfRoundClosing() {
		return dateOfRoundClosing;
	}

	public void setDateOfRoundClosing(Date dateOfRoundClosing) {
		this.dateOfRoundClosing = dateOfRoundClosing;
	}

	public Long getFundingAmount() {
		return fundingAmount;
	}

	public void setFundingAmount(Long fundingAmount) {
		this.fundingAmount = fundingAmount;
	}

	public Long getRaisedAmount() {
		return raisedAmount;
	}

	public void setRaisedAmount(Long raisedAmount) {
		this.raisedAmount = raisedAmount;
	}

	public Float getPercentage() {
		return percentage;
	}

	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}

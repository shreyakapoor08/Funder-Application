package com.asdc.funderbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Entity
@Table(name="Comments")
@NoArgsConstructor
public class Comment {

    @Id
    @Column(name="COM_ID")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comId;

    @Column(name="USER_ID")
    private Long userId;

    @Column(name="FULL_NAME")
    private String fullName;

    @Column(name="AVATAR_URL")
    private String avatarUrl;

    @Lob
    @Column(name="TEXT",length=10000)
    private String text;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_COMMENT_ID")
    private List<Comment> replies;

    @Column(name = "PARENT_COMMENT_ID")
    private Long parentCommentId;

    // The below productId variable is for setting the value of product Id in the child comments.
    @Column(name="PRODUCT_ID", insertable = true, updatable = true)
    private Long productId;

	public Long getComId() {
		return comId;
	}

	public void setComId(Long comId) {
		this.comId = comId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Comment> getReplies() {
		return replies;
	}

	public void setReplies(List<Comment> replies) {
		this.replies = replies;
	}

	public Long getParentCommentId() {
		return parentCommentId;
	}

	public void setParentCommentId(Long parentCommentId) {
		this.parentCommentId = parentCommentId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
}

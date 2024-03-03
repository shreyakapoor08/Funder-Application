package com.asdc.funderbackend.entity;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="BLOG")
@NoArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BLOG_ID")
    private Long blog_id;

    @Column(name="TITLE",length = 100)
    private String title;
    // columnDefinition="TEXT"
    @Lob
    @Column(name="CONTENT",length = 10000)
    private String content;

    @Column(name="IMAGE_URL",length = 10000)
    private String imageName;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name="ADDED_DATE",length = 10000)
    private Date addedDate;

    @Column(name="BLOG_TYPE",length = 100)
    private String blogType;

    @Column(name="SHORT_DESCRIPTION",length = 500)
    private String shortDescription;

    // Adding transient as this is the only formatted date used for displaying on UI.
    @Transient
    private String addedDateFormatted;


    public String getFormattedAddedDate(){
        if (addedDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");
            return sdf.format(addedDate);
        }
        return null;
    }

    public void setFormattedAddedDate(String addedDateFormatted){
        this.addedDateFormatted = addedDateFormatted;
    }
    public Long getBlog_id() {
        return blog_id;
    }
    public void setBlog_id(Long blog_id) {
        this.blog_id = blog_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getBlogType() {
        return blogType;
    }

    public void setBlogType(String blogType) {
        this.blogType = blogType;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getAddedDateFormatted() {
        return addedDateFormatted;
    }

    public void setAddedDateFormatted(String addedDateFormatted) {
        this.addedDateFormatted = addedDateFormatted;
    }
}

package com.asdc.funderbackend.controller;

import com.asdc.funderbackend.entity.Blog;
import com.asdc.funderbackend.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The type Blog controller.
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * Get all blogs response entity.
     *
     * @return the response entity
     */
// Get All Blogs
    @GetMapping("/getAllBlogs")
    public ResponseEntity<List<Blog>> getAllBlogs(){
        List<Blog> allBlogs = blogService.getAllBlogs();
        return new ResponseEntity<List<Blog>>(allBlogs,HttpStatus.OK);
    }

    /**
     * Gets blog by id.
     *
     * @param blog_id the blog id
     * @return the blog by id
     */
// Get Blogs by Id
    @GetMapping("/getBlog/{blogId}")
    public ResponseEntity<Blog> getBlogById(@PathVariable(value = "blogId") Long blog_id){
        Blog blog = blogService.getBlogById(blog_id);
        return new ResponseEntity<Blog>(blog,HttpStatus.OK);
    }
}
package com.asdc.funderbackend.service;

import com.asdc.funderbackend.entity.Blog;
import java.util.List;

/**
 * The interface Blog service.
 */
public interface BlogService {

    /**
     * Gets all blogs.
     *
     * @return the all blogs
     */
// get all blogs
    List<Blog> getAllBlogs();

    /**
     * Gets blog by id.
     *
     * @param blog_id the blog id
     * @return the blog by id
     */
// get single blog
    Blog getBlogById(Long blog_id);

}

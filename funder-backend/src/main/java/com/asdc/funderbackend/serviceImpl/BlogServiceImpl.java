package com.asdc.funderbackend.serviceImpl;

import com.asdc.funderbackend.entity.Blog;
import com.asdc.funderbackend.repository.BlogRepo;
import com.asdc.funderbackend.repository.UserRepo;
import com.asdc.funderbackend.service.BlogService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * The type Blog service.
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private UserRepo userRepo;


    @Override
    public List<Blog> getAllBlogs() {
        List<Blog> allBlogs = blogRepo.findAll();
        return allBlogs;
    }

    @Override
    public Blog getBlogById(Long blog_id) {
        Blog blog = blogRepo.findById(blog_id).orElseThrow(() -> new EntityNotFoundException("No Blogs found!!!"));
        return blog;
    }

}

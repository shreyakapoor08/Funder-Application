package com.asdc.funderbackend.controller;

import com.asdc.funderbackend.entity.Blog;
import com.asdc.funderbackend.service.BlogService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.asdc.funderbackend.controller.BlogController;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.text.DateFormat;
import static org.mockito.Mockito.when;

/*
Test class for Blog Controller.
 */
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class BlogControllerTest {

    @InjectMocks
    private BlogController blogController;

    @Mock
    private BlogService blogService;

    private MockMvc mockMvc;

    private static Long blog_id = 1L;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
    }

    /**
     * Test for checking all blogs API is working.
     *
     */
    @Test
    void getAllBlogsTest() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date addDateBlog1 = df.parse("2023-10-11");
        Date addDateBlog2 = df.parse("2023-09-05");

        Blog blog1 = new Blog();
        blog1.setTitle("Next-Level Gaming On-The-Go");
        blog1.setContent("Embark on a gaming revolution.");
        blog1.setBlogType("Technology");
        blog1.setImageName("https://rb.gy/l5r8m8");
        blog1.setAddedDate(addDateBlog1);
        blog1.setShortDescription("Gaming experience like never before.");
        Blog blog2 = new Blog();

        blog2.setTitle("The Top 10 Crowdfunding Campaigns of 2022");
        blog2.setContent("Discover innovation at its peak.");
        blog2.setBlogType("Technology");
        blog2.setImageName("https://rb.gy/l5r8m8");
        blog2.setAddedDate(addDateBlog2);
        blog2.setShortDescription("These campaigns reshaped the crowdfunding landscape.");

        List<Blog> blogs = Arrays.asList(blog1,blog2);

        when(blogService.getAllBlogs()).thenReturn(blogs);

        mockMvc.perform(MockMvcRequestBuilders.get("/blog/getAllBlogs"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    /*
    * Test for checking get blogs by ID API is working.
    */

    @Test
    public void getBlogByIdTest() throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date addDateBlog2 = df.parse("2023-09-05");
        Blog blog = new Blog();
        blog.setBlog_id(blog_id);
        blog.setTitle("The Top 10 Crowdfunding Campaigns of 2022");
        blog.setBlogType("Business");
        blog.setContent("Discover innovation at its finest.");
        blog.setImageName("https://rb.gy/l5r8m8");
        blog.setAddedDate(addDateBlog2);
        blog.setShortDescription("These campaigns reshaped the crowdfunding landscape.");

        when(blogService.getBlogById(1L)).thenReturn(blog);

        mockMvc.perform(MockMvcRequestBuilders.get("/blog/getBlog/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.blog_id").value(blog.getBlog_id()));
    }




}


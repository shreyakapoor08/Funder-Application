package com.asdc.funderbackend.controller;

import com.asdc.funderbackend.entity.Comment;
import com.asdc.funderbackend.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
/*
* Comment Controller test file.
 */
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    private MockMvc mockMvc;

    private static Long productId = 1L,firstCommentId=1L,secondCommentId=2L;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

    }
    /*
    * Get Comment by Product ID API test case.
     */
    @Test
    public void getCommentByProductIdTest() throws Exception{
        Comment firstComment = new Comment();
        firstComment.setComId(firstCommentId);
        firstComment.setProductId(productId);
        firstComment.setParentCommentId(null);
        firstComment.setAvatarUrl("https://ui-avatars.com/api/name=Bhautik&background=random");
        firstComment.setFullName("Bhautik Koshiya");
        firstComment.setText("Product looks good!!!");

        Comment secondComment = new Comment();
        secondComment.setComId(secondCommentId);
        secondComment.setProductId(productId);
        secondComment.setParentCommentId(firstCommentId);
        secondComment.setAvatarUrl("https://ui-avatars.com/api/name=Parth&background=random");
        secondComment.setFullName("Parth Patel");
        secondComment.setText("Excited for using it when it launches.");

        List<Comment> comments = Arrays.asList(firstComment,secondComment);
        when(commentService.getCommentByProductId(productId)).thenReturn(comments);

        mockMvc.perform(MockMvcRequestBuilders.get("/comments/getCommentsByProduct/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
    /*
    * Get Comment By Id API test case.
     */
    @Test
    public void getCommentByIdTest() throws Exception{
        Comment firstComment = new Comment();
        firstComment.setComId(firstCommentId);
        firstComment.setProductId(productId);
        firstComment.setParentCommentId(null);
        firstComment.setAvatarUrl("https://ui-avatars.com/api/name=Bhautik&background=random");
        firstComment.setFullName("Bhautik Koshiya");
        firstComment.setText("Product looks good!!!");

        when(commentService.getCommentById(firstCommentId)).thenReturn(firstComment);

        mockMvc.perform(MockMvcRequestBuilders.get("/comments/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    /*
    * Save Comment API test case.
     */
    @Test
   public void saveCommentTest(){
        Comment firstComment = new Comment();
        firstComment.setComId(firstCommentId);
        firstComment.setProductId(productId);
        firstComment.setParentCommentId(null);
        firstComment.setAvatarUrl("https://ui-avatars.com/api/name=Bhautik&background=random");
        firstComment.setFullName("Bhautik Koshiya");
        firstComment.setText("Product looks good!!!");
        when(commentController.saveComment(firstComment)).thenReturn(firstComment);
        Comment actualComment = commentController.saveComment(firstComment);
        verify(commentService, times(1)).saveComment(firstComment);
        assertEquals(firstComment,actualComment);
    }

}

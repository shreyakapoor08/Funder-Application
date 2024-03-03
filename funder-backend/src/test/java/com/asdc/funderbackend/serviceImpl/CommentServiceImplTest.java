package com.asdc.funderbackend.serviceImpl;

import com.asdc.funderbackend.entity.Blog;
import com.asdc.funderbackend.entity.Comment;
import com.asdc.funderbackend.repository.CommentRepo;
import com.asdc.funderbackend.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for CommentServiceImplementation.
 */
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepo commentRepo;

    private MockMvc mockMvc;

    @InjectMocks
    private CommentServiceImpl commentService;

    private static Long commentId = 1L,productId = 1L;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(commentRepo).build();
    }

    /**
     * Test for verifying the comment fetched on commentId.
     *
     */
    @Test
    public void getCommentByIdTest() throws Exception{
        Comment mockComment = new Comment();
        mockComment.setComId(commentId);
        mockComment.setText("Nice product!!!");
        mockComment.setParentCommentId(null);
        mockComment.setFullName("Bhautik Koshiya");
        mockComment.setAvatarUrl("https://ui-avatars.com/api/name=Bhautik&background=random");
        mockComment.setProductId(productId);
        when(commentRepo.findById(commentId)).thenReturn(Optional.of(mockComment));
        Comment actualComment = commentService.getCommentById(commentId);
        assertThat(actualComment).isEqualTo(mockComment);
    }

    /**
     * Test for verifying that the fetched comment by comment ID is not null.
     */
    @Test
    public void getCommentByIdNotNullTest() {
        Comment mockComment = new Comment();
        when(commentRepo.findById(commentId)).thenReturn(Optional.of(mockComment));
        Comment actualComment = commentService.getCommentById(commentId);
        assertThat(actualComment).isNotNull();
    }

    /**
     * Test for verifying that the fetched comment by comment ID is empty.
     */
    @Test
    public void getCommentByIdEmptyTest() {
        Comment mockComment = new Comment();
        when(commentRepo.findById(commentId)).thenReturn(Optional.of(mockComment));
        Comment actualComment = commentService.getCommentById(commentId);
        assertThat(actualComment).isEqualTo(mockComment);
    }

    /**
     * Test for verifying that the comments is fetched on a particular product id.
     */
    @Test
    public void getCommentByProductIdTest() {
        List<Comment> expectedComments = Arrays.asList(new Comment(), new Comment());
        when(commentRepo.findByProductId(productId)).thenReturn(expectedComments);
        List<Comment> actualComments = commentService.getCommentByProductId(productId);
        assertEquals(expectedComments, actualComments);
    }

    /**
     * Test for verifying the comments by product ID is empty.
     */
    @Test
    public void getCommentByProductIdEmptyTest() {
        List<Comment> expectedComments = Arrays.asList();
        when(commentRepo.findByProductId(productId)).thenReturn(expectedComments);
        List<Comment> actualComments = commentService.getCommentByProductId(productId);
        assertEquals(expectedComments, actualComments);
    }

    /**
     * Test for verifying the comments by product ID is null.
     */
    @Test
    public void getCommentByProductIdNullTest() {
        when(commentRepo.findByProductId(productId)).thenReturn(null);
        List<Comment> actualComments = commentService.getCommentByProductId(productId);
        assertNull(actualComments);
    }

    /**
     * Test for verifying the comment is getting saved.
     */
    @Test
    public void saveCommentTest(){
        Comment mockComment = new Comment();
        mockComment.setComId(commentId);
        mockComment.setText("Nice product!!!");
        mockComment.setParentCommentId(null);
        mockComment.setFullName("Bhautik Koshiya");
        mockComment.setAvatarUrl("https://ui-avatars.com/api/name=Bhautik&background=random");
        mockComment.setProductId(productId);
        when(commentRepo.save(mockComment)).thenReturn(mockComment);
        Comment actualComment = commentService.saveComment(mockComment);
        assertThat(actualComment).isEqualTo(mockComment);

    }

    /**
     * Test for verifying the comment getting saved is not null.
     */
    @Test
    public void saveCommentNotNullTest(){
        Comment mockComment = new Comment();
        mockComment.setComId(commentId);
        mockComment.setText("Nice product!!!");
        mockComment.setParentCommentId(null);
        mockComment.setFullName("Bhautik Koshiya");
        mockComment.setAvatarUrl("https://ui-avatars.com/api/name=Bhautik&background=random");
        mockComment.setProductId(productId);
        when(commentRepo.save(mockComment)).thenReturn(mockComment);
        Comment actualComment = commentService.saveComment(mockComment);
        assertThat(actualComment).isNotNull();
    }



}




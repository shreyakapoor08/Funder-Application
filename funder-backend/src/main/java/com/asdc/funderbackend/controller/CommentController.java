package com.asdc.funderbackend.controller;


import com.asdc.funderbackend.entity.Comment;
import com.asdc.funderbackend.service.CommentService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The type Comment controller.
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    /**
     * Instantiates a new Comment controller.
     *
     * @param commentService the comment service
     */
    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }
//    @GetMapping("/getAllComments")
//    public List<Comment> getAllComments(){
//        return commentService.getAllComments();
//    }

    /**
     * Get comment by product id list.
     *
     * @param productId the product id
     * @return the list
     */
    @GetMapping("/getCommentsByProduct/{productId}")
    public List<Comment> getCommentByProductId(@PathVariable Long productId){
        return  commentService.getCommentByProductId(productId);
    }

    /**
     * Get comment by id comment.
     *
     * @param commentId the comment id
     * @return the comment
     */
    @GetMapping("/{commentId}")
    public Comment getCommentById(@PathVariable Long commentId){
        return commentService.getCommentById(commentId);
    }

    /**
     * Save comment comment.
     *
     * @param comment the comment
     * @return the comment
     */
    @PostMapping("/saveComment/")
    public Comment saveComment(@RequestBody Comment comment){
        return commentService.saveComment(comment);
    }


}

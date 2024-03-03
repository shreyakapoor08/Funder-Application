package com.asdc.funderbackend.service;

import com.asdc.funderbackend.entity.Comment;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * The interface Comment service.
 */
public interface CommentService {
    //List<Comment> getAllComments();

    /**
     * Gets comment by product id.
     *
     * @param productId the product id
     * @return the comment by product id
     */
    public List<Comment> getCommentByProductId(Long productId);

    /**
     * Gets comment by id.
     *
     * @param commentId the comment id
     * @return the comment by id
     */
    Comment getCommentById(Long commentId);

    /**
     * Save comment comment.
     *
     * @param comment the comment
     * @return the comment
     */
    Comment saveComment(Comment comment);

}

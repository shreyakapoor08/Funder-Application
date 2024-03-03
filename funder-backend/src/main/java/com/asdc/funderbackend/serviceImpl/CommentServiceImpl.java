package com.asdc.funderbackend.serviceImpl;

import com.asdc.funderbackend.entity.Comment;
import com.asdc.funderbackend.repository.CommentRepo;
import com.asdc.funderbackend.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * The type Comment service.
 */
@Service
public class CommentServiceImpl implements CommentService {



    @Autowired
    private CommentRepo commentRepo;

//    @Override
//    public List<Comment> getAllComments() {
//        return commentRepo.findAll();
//    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepo.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }

    public List<Comment> getCommentByProductId(@PathVariable Long productId){
        return commentRepo.findByProductId(productId);
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepo.save(comment);
    }

}

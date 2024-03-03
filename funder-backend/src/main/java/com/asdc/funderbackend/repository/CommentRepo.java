package com.asdc.funderbackend.repository;


import com.asdc.funderbackend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Comment repo.
 */
@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {

    /**
     * Find by product id list.
     *
     * @param productId the product id
     * @return the list
     */
    List<Comment> findByProductId(Long productId);
}

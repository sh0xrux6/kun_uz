package com.example.repository;

import com.example.Entity.CommentLikeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity,Integer> {

    void deleteByCommentIdAndProfileId(Integer commentId, Integer userId);

    CommentLikeEntity findByCommentIdAndProfileId(Integer commentId, Integer userId);

}

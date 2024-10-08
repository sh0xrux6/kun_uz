package com.example.service;

import com.example.Entity.CommentLikeEntity;
import com.example.Enum.StatusLikeDis;
import com.example.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;


    public boolean like(int commentId, int userId) {
        if(commentLikeRepository.findByCommentIdAndProfileId(commentId,userId) == null) {
            CommentLikeEntity entity = new CommentLikeEntity();
            entity.setCommentId(commentId);
            entity.setProfileId(userId);
            entity.setStatus(StatusLikeDis.LIKE);
            commentLikeRepository.save(entity);
            return true;
        } else if (commentLikeRepository.findByCommentIdAndProfileId(commentId,userId) != null) {
            CommentLikeEntity entity = commentLikeRepository.findByCommentIdAndProfileId(commentId,userId);
            entity.setStatus(StatusLikeDis.LIKE);
            commentLikeRepository.save(entity);
            return true;
        }
        return false;
    }

    public boolean dislike(int commentId, int userId) {
        if(commentLikeRepository.findByCommentIdAndProfileId(commentId,userId) == null) {
            CommentLikeEntity entity = new CommentLikeEntity();
            entity.setCommentId(commentId);
            entity.setProfileId(userId);
            entity.setStatus(StatusLikeDis.DISLIKE);
            commentLikeRepository.save(entity);
            return true;
        } else if (commentLikeRepository.findByCommentIdAndProfileId(commentId,userId) != null) {
            CommentLikeEntity entity = commentLikeRepository.findByCommentIdAndProfileId(commentId,userId);
            entity.setStatus(StatusLikeDis.DISLIKE);
            commentLikeRepository.save(entity);
            return true;
        }
        return false;
    }

    public boolean remove(int commentId,int userId) {
        if (commentLikeRepository.existsById(commentId)) {
            commentLikeRepository.deleteByCommentIdAndProfileId(commentId,userId);
            return true;
        }
        return false;
    }

}

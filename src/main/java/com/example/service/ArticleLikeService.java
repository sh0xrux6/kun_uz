package com.example.service;

import com.example.Entity.ArticleLikeEntity;
import com.example.Enum.StatusLikeDis;
import com.example.repository.ArticleLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public boolean like(UUID articleId, int userId) {
        if(articleLikeRepository.findAllByArticleIdAndProfileId(articleId, userId) == null) {
            ArticleLikeEntity entity = new ArticleLikeEntity();
            entity.setArticleId(articleId);
            entity.setProfileId(userId);
            entity.setStatus(StatusLikeDis.LIKE);
            articleLikeRepository.save(entity);
            return true;
        }
        else if (articleLikeRepository.findAllByArticleIdAndProfileId(articleId,userId) != null) {
            ArticleLikeEntity entity = articleLikeRepository.findAllByArticleIdAndProfileId(articleId,userId);
            entity.setStatus(StatusLikeDis.LIKE);
            articleLikeRepository.save(entity);
            return true;
        }
        return false;
    }

    public boolean dislike(UUID articleId, int userId) {
        if(articleLikeRepository.findAllByArticleIdAndProfileId(articleId, userId) == null) {
            ArticleLikeEntity entity = new ArticleLikeEntity();
            entity.setArticleId(articleId);
            entity.setProfileId(userId);
            entity.setStatus(StatusLikeDis.DISLIKE);
            articleLikeRepository.save(entity);
            return true;
        }
        else if (articleLikeRepository.findAllByArticleIdAndProfileId(articleId,userId) != null) {
            ArticleLikeEntity entity = articleLikeRepository.findAllByArticleIdAndProfileId(articleId,userId);
            entity.setStatus(StatusLikeDis.DISLIKE);
            articleLikeRepository.save(entity);
            return true;
        }
        return false;
    }

    public boolean remove(UUID articleId, int userId) {
        if(articleLikeRepository.findAllByArticleIdAndProfileId(articleId, userId) != null) {
            articleLikeRepository.delete(articleLikeRepository.findAllByArticleIdAndProfileId(articleId,userId));
            return true;
        }
        return false;
    }

}

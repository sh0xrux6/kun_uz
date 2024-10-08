package com.example.repository;

import com.example.Entity.ArticleLikeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity,Integer> {


    ArticleLikeEntity findAllByArticleIdAndProfileId(UUID articleId, Integer profileId);
}

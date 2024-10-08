package com.example.repository;

import com.example.Entity.ArticlesTypesEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticlesTypesRepository extends CrudRepository<ArticlesTypesEntity,Integer> {

    void deleteByArticleId(UUID articleId);

    @Query("select a.articleTypeId from ArticlesTypesEntity as a where a.articleId=:articleId")
    List<Integer> getAllArticleTypeIdList(UUID articleId);

    @Transactional
    @Modifying
    @Query("delete from ArticlesTypesEntity as a where a.articleId=:articleId and a.articleTypeId=:typeId")
    void deleteByArticleIdAndTypeId(@Param("articleId") UUID articleId, @Param("typeId") Integer typeId);
}

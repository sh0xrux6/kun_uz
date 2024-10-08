package com.example.repository;

import com.example.Entity.SavedArticleEntity;
import com.example.mapper.SavedArticleMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity,Integer> {

    @Query(value = "SELECT ar.id, a.id, a.title, a.description, img.id, img.path\n" +
            "FROM saved_article AS ar\n" +
            "INNER JOIN article AS a ON a.id = ar.article_id\n" +
            "INNER JOIN attach AS img ON img.id = ar.image_id\n" +
            "WHERE ar.profile_id = ?1;", nativeQuery = true)
    List<SavedArticleMapper> getAll(@Param("id") int profile_id);

}

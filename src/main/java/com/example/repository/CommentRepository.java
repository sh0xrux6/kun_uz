package com.example.repository;

import com.example.Entity.CommentEntity;
import com.example.mapper.CommentProfileInfoMapper;
import com.example.mapper.CommentListPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {

    Optional<CommentEntity> getAllById(@Param("id") Integer id);

    @Query(value = "select c.id,c.created_date,c.updates_date,c.profile_id,p.name,p.surname from comment as c inner join profile as p on p.id = c.profile_id",nativeQuery = true)
    List<CommentProfileInfoMapper> getByArticleId(@Param("Uuid") UUID id);

    @Query(value = "select c.id,c.created_date,c.updates_date,c.profile_id,p.name,p.surname,c.content,a.uuid,a.title,c.reply_id from comment as c " +
            "inner join profile as p on p.id = c.profile_id  inner join article as a on a.uuid =  c.article_id;",nativeQuery = true)
    Page<CommentListPagination> getCommentListPagination(Pageable pageable);

    @Query(value = "c.id,c.created_date,c.updates_date,c.profile_id,p.name,p.surname from comment as c where reply_id = ?1 ",nativeQuery = true)
    List<CommentProfileInfoMapper> getRepliedCommentList(@Param("id") int commentId);
}

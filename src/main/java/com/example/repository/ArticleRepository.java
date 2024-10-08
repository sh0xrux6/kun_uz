package com.example.repository;

import com.example.Entity.ArticleEntity;
import com.example.mapper.ArticleShortInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity,UUID>, PagingAndSortingRepository<ArticleEntity, UUID> {

    @Query(value = "select * from article where type_id = ?1 and visible = true and status = 'PUBLISHED' order by created_date DESC limit 5",nativeQuery = true)
    List<ArticleShortInfoMapper> getLastFive(@Param("type") int type);

    @Query(value = "select * from article where type_id = ?1 and visible = true and status = 'PUBLISHED' order by created_date DESC limit 3",nativeQuery = true)
    List<ArticleShortInfoMapper> getLastThree(@Param("type") int type);

    @Query(value = "select * from article as a where a.id <> ?1  and visible = true and status = 'PUBLISHED' order by created_date DESC limit 8",nativeQuery = true)
    List<ArticleShortInfoMapper>getLastEight(@Param("id") int id);

    @Query(value = "select * from article where status = 'PUBLISHED' and visible = true order by view_count DESC limit 4",nativeQuery = true)
    List<ArticleShortInfoMapper> getMostViewed();

    @Query(value = "select * from article where type_id = ?1 and region_id = ?2  and status = 'PUBLISHED' and visible = true order by created_date limit 5",nativeQuery = true)
    List<ArticleEntity> getByTypeAndRegion(@Param("type") int type,@Param("region") int region);

    Page<ArticleShortInfoMapper> findAllByRegionId(@Param("regionId") int regionId, Pageable pageable);

    @Query(value = "select * from article where category_id=?1 and status = 'PUBLISHED' and visible = true order by created_date ",nativeQuery = true)
    List<ArticleShortInfoMapper> findAllByCategoryId(@Param("categoryId") int categoryId);

    @Query(value = "SELECT * FROM article a WHERE a.categoryId = :categoryId",nativeQuery = true)
    Page<ArticleShortInfoMapper> findAllByCategoryId(@Param("categoryId") int categoryId,Pageable pageable);

    @Query(value = "select * from article as a where a.type_id = ?1 and a.id <>=?2  and status = 'PUBLISHED' and visible = true order by created_date limit 4",nativeQuery = true)
    List<ArticleShortInfoMapper> getLastFourByTypeExcept(@Param("type") int type,@Param("id") int id);

    @Query(value = "update article set view_count = view_count + 1 where uuid = ?1",nativeQuery = true)
    int increaseViewCountById(@Param("id") UUID id);

    @Query(value = "update article set share_count = share_count + 1 where uuid = ?1",nativeQuery = true)
    int increaseShareCount(UUID id);



}

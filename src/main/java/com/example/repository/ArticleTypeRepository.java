package com.example.repository;

import com.example.Entity.ArticleTypeEntity;
import com.example.dto.ArticleTypeDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity,Integer> {
    @Query("select new com.example.dto.ArticleTypeDTO(id,orderNum,nameEn) from ArticleTypeEntity where visible= true order by orderNum")
    List<ArticleTypeDTO> findAllByNameEn();

    @Query("select new com.example.dto.ArticleTypeDTO(id,orderNum,nameRu) from ArticleTypeEntity where visible= true order by orderNum")
    List<ArticleTypeDTO> findAllByNameRu();

    @Query("select new com.example.dto.ArticleTypeDTO(id,orderNum,nameUz) from ArticleTypeEntity where visible= true order by orderNum")
    List<ArticleTypeDTO> findAllByNameUz();


}

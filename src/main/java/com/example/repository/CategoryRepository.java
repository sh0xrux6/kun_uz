package com.example.repository;


import com.example.Entity.CategoryEntity;
import com.example.dto.CategoryDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    @Query(value = "select * from category order by order_num", nativeQuery = true)
    List<CategoryEntity> getAllByAndOrderByOrderNum();

    @Query("select new com.example.dto.CategoryDTO(id,orderNum,nameEn) from CategoryEntity where visible= true order by orderNum")
    List<CategoryDTO> findAllByNameEn();

    @Query("select new com.example.dto.CategoryDTO(id,orderNum,nameRu) from CategoryEntity where visible= true order by orderNum")
    List<CategoryDTO> findAllByNameRu();

    @Query("select new com.example.dto.CategoryDTO(id,orderNum,nameUz) from CategoryEntity where visible= true order by orderNum")
    List<CategoryDTO> findAllByNameUz();
}

package com.example.repository;

import com.example.dto.RegionDTO;
import com.example.Entity.RegionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity,Integer> {

    @Query("select new com.example.dto.RegionDTO(id, orderNum, nameEn) from RegionEntity where visible= true order by orderNum")
    List<RegionDTO> findAllByNameEn();

    @Query("select new com.example.dto.RegionDTO(id, orderNum, nameRu) from RegionEntity where visible= true order by orderNum")
    List<RegionDTO> findAllByNameRu();

    @Query("select new com.example.dto.RegionDTO(id, orderNum, nameUz) from RegionEntity where visible= true order by orderNum")
    List<RegionDTO> findAllByNameUz();
}

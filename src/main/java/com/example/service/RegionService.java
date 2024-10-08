package com.example.service;

import com.example.Entity.RegionEntity;
import com.example.Enum.Language;
import com.example.dto.RegionDTO;
import com.example.exp.AppBadRequestException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO dto,int prtId) {
        RegionEntity region = toEntity(dto);
        region.setPrtId(prtId);
        regionRepository.save(region);
        dto.setId(region.getId());
        dto.setCreatedDate(region.getCreatedDate());

        return dto;
    }

    public boolean update(int id,RegionDTO dto,int prtId){
        Optional<RegionEntity> entity = regionRepository.findById(id);

        if (entity.isEmpty()){
            throw new AppBadRequestException("Region with id " + id + " does not exists");
        }
        RegionEntity region = entity.get();
        region.setOrderNum(dto.getOrderNumber());
        region.setNameUz(dto.getNameUz());
        region.setNameRu(dto.getNameRu());
        region.setNameEn(dto.getNameEn());
        region.setPrtId(prtId);
        regionRepository.save(region);

        dto.setId(region.getId());
        dto.setCreatedDate(region.getCreatedDate());

        return true;
    }

    public boolean delete(int id){
        if (regionRepository.existsById(id)){
            regionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<RegionDTO> getAll(){
        List<RegionDTO> dto = new ArrayList<>();
        Iterable<RegionEntity> list = regionRepository.findAll();

        list.forEach(e->dto.add(toDto(e)));
        return dto;
    }

    public RegionDTO toDto(RegionEntity e) {
        RegionDTO dto = new RegionDTO();
        dto.setId(e.getId());
        dto.setNameUz(e.getNameUz());
        dto.setNameRu(e.getNameRu());
        dto.setNameEn(e.getNameEn());
        dto.setCreatedDate(e.getCreatedDate());
        dto.setOrderNumber(e.getOrderNum());
        return dto;
    }

    public RegionEntity toEntity(RegionDTO dto){
        RegionEntity region = new RegionEntity();

        region.setOrderNum(dto.getOrderNumber());
        region.setNameUz(dto.getNameUz());
        region.setNameRu(dto.getNameRu());
        region.setNameEn(dto.getNameEn());

        return region;
    }

    public List<RegionDTO> getByLangV2(Language lang){
        List<RegionDTO> dtoList;
        switch (lang){
            case en -> dtoList = regionRepository.findAllByNameEn();
            case ru -> dtoList = regionRepository.findAllByNameRu();
            default -> dtoList = regionRepository.findAllByNameUz();
        }
        return dtoList;
    }

}

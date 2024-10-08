package com.example.service;

import com.example.Entity.ArticleTypeEntity;
import com.example.Enum.Language;
import com.example.dto.ArticleTypeDTO;
import com.example.exp.AppBadRequestException;
import com.example.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleRepository;


    public ArticleTypeDTO create(ArticleTypeDTO dto,int prt_id) {
        ArticleTypeEntity region = toEntity(dto);
        region.setPrtId(prt_id);
        articleRepository.save(region);
        dto.setId(region.getId());
        dto.setCreatedDate(region.getCreatedDate());

        return dto;
    }

    public boolean update(int id,ArticleTypeDTO dto,int prtId){
        Optional<ArticleTypeEntity> entity = articleRepository.findById(id);

        if (entity.isEmpty()) {
            throw new AppBadRequestException("Article with id " + id + " does not exists");
        }

        ArticleTypeEntity region = entity.get();
        region.setOrderNum(dto.getOrderNumber());
        region.setPrtId(prtId);
        region.setNameUz(dto.getNameUz());
        region.setNameRu(dto.getNameRu());
        region.setNameEn(dto.getNameEn());
        articleRepository.save(region);

        return true;
    }

    public boolean delete(int id){
        if (articleRepository.existsById(id)){
            articleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ArticleTypeDTO> getAll(){
        Iterable<ArticleTypeEntity> entities = articleRepository.findAll();
        List<ArticleTypeDTO> dto = new ArrayList<>();
        entities.forEach(e->dto.add(toDto(e)));

        return dto;
    }

    public List<ArticleTypeDTO> getByLangV2(Language lang){
        List<ArticleTypeDTO> dtoList;

        switch (lang){
            case en -> dtoList = articleRepository.findAllByNameEn();
            case ru -> dtoList = articleRepository.findAllByNameRu();
            default -> dtoList = articleRepository.findAllByNameUz();
        }

        return dtoList;
    }

    public ArticleTypeDTO toDto(ArticleTypeEntity e) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(e.getId());
        dto.setNameUz(e.getNameUz());
        dto.setNameRu(e.getNameRu());
        dto.setNameEn(e.getNameEn());
        dto.setCreatedDate(e.getCreatedDate());
        dto.setOrderNumber(e.getOrderNum());
        return dto;
    }

    public ArticleTypeEntity toEntity(ArticleTypeDTO dto){
        ArticleTypeEntity region = new ArticleTypeEntity();

        region.setOrderNum(dto.getOrderNumber());
        region.setNameUz(dto.getNameUz());
        region.setNameRu(dto.getNameRu());
        region.setNameEn(dto.getNameEn());

        return region;
    }
}

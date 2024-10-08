package com.example.service;

import com.example.Enum.Language;
import com.example.dto.CategoryDTO;
import com.example.Entity.CategoryEntity;

import com.example.exp.AppBadRequestException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    // Category(1,2,3,4,5)
    /* 1. Create  (ADMIN)
        (order_number,name_uz, name_ru, name_en)
    2. Update by id (ADMIN)
        (order_number,name_uz, name_ru, name_en)
    3. Delete by id (ADMIN)
    4. Get List (ADMIN) - order by order_number
        (id,order_number,name_uz, name_ru, name_en,visible,created_date)
    5. Get By Lang (Language keladi shu language dagi name larini berib yuboramiz)
        (id,order_number,name) (name ga tegishli name_... dagi qiymat qo'yiladi.)*/

    public CategoryDTO create(CategoryDTO categoryDTO,int prtId){
        CategoryEntity category = toEntity(categoryDTO);
        category.setPrtId(prtId);
        categoryRepository.save(category);
        categoryDTO.setId(category.getId());
        categoryDTO.setCreatedDate(category.getCreatedDate());

        return categoryDTO;
    }

    public boolean update(int id,CategoryDTO categoryDTO,int prtId){
        if (!categoryRepository.existsById(id)){
            throw new AppBadRequestException("Category dose not exists");
        }
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);

        if (categoryEntity.isEmpty()){
            throw new AppBadRequestException("Category dose not exists");
        }

        CategoryEntity category = categoryEntity.get();
        category.setOrderNum(categoryDTO.getOrderNum());
        category.setNameUz(categoryDTO.getNameUz());
        category.setNameRu(categoryDTO.getNameRu());
        category.setNameEn(categoryDTO.getNameEn());
        category.setPrtId(prtId);
        categoryRepository.save(category);
        categoryDTO.setId(category.getId());
        categoryDTO.setCreatedDate(category.getCreatedDate());

        return true;
    }

    public boolean delete(int id){
        if (categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<CategoryDTO> getList(){
        List<CategoryEntity> list = categoryRepository.getAllByAndOrderByOrderNum();
        List<CategoryDTO> dto = new ArrayList<>();

        list.forEach(l->dto.add(toDTO(l)));
        return dto;
    }

    public List<CategoryDTO> getByLangV2(Language lang){
        List<CategoryDTO> dtoList;

        switch (lang){
            case en -> dtoList = categoryRepository.findAllByNameEn();
            case ru -> dtoList = categoryRepository.findAllByNameRu();
            default -> dtoList = categoryRepository.findAllByNameUz();
        }

        return dtoList;
    }

    public CategoryDTO toDTO(CategoryEntity categoryEntity){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryEntity.getId());
        categoryDTO.setNameUz(categoryEntity.getNameUz());
        categoryDTO.setNameRu(categoryEntity.getNameRu());
        categoryDTO.setNameEn(categoryEntity.getNameEn());
        categoryDTO.setOrderNum(categoryEntity.getOrderNum());
        return categoryDTO;
    }

    public CategoryEntity toEntity(CategoryDTO categoryDTO){
        CategoryEntity category = new CategoryEntity();

        category.setOrderNum(categoryDTO.getOrderNum());
        category.setNameUz(categoryDTO.getNameUz());
        category.setNameRu(categoryDTO.getNameRu());
        category.setNameEn(categoryDTO.getNameEn());

        return category;
    }
}

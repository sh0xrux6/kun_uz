package com.example.service;


import com.example.Entity.ArticlesTypesEntity;
import com.example.repository.ArticlesTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArticlesTypesService {
    @Autowired
    private ArticlesTypesRepository articlesTypesRepository;

    public void create(UUID articleId, List<Integer> articleTypeIdList){
        articleTypeIdList.forEach(typeId->{
            save(articleId, typeId);
        });
    }

    public void save(UUID articleId, Integer articleTypeId) {
        ArticlesTypesEntity entity = new ArticlesTypesEntity();
        entity.setArticleId(articleId);
        entity.setArticleTypeId(articleTypeId);
        articlesTypesRepository.save(entity);
    }

    public void merge(UUID articleId, List<Integer> newList) {

//        new = [1,2,7,8]
        if(newList==null){
            articlesTypesRepository.deleteByArticleId(articleId);
            return;
        }

//        old = [1,2,3,4,5]
        List<Integer> oldList = articlesTypesRepository.getAllArticleTypeIdList(articleId);
        for (Integer typeId: newList){
            if(!oldList.contains(typeId)){
                save(articleId, typeId); // create
            }
        }

//        final = [1,2,3,4,5,7,8]
        for (Integer typeId: oldList){
            if(!newList.contains(typeId)){
                articlesTypesRepository.deleteByArticleIdAndTypeId(articleId, typeId);
            }
        }
    }
}

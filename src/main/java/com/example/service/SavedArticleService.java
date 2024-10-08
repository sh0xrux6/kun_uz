package com.example.service;

import com.example.Entity.SavedArticleEntity;
import com.example.mapper.SavedArticleMapper;
import com.example.repository.SavedArticleRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SavedArticleService {

    @Autowired
    private SavedArticleRepository savedArticleRepository;
    /*5.
     SavedArticle
    1. Create (ANY)
        article_id
    2. Delete (ANY)
        article_id
    3. Get Profile Saved Article List (ANY)
        (id,article(id,title,description,image(id,url)))*/

    public boolean create(int pr_id, UUID article_id){
        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(article_id);
        entity.setProfileId(pr_id);
        savedArticleRepository.save(entity);
        return true;
    }

    public boolean delete(int saved_article_id){
        if(saved_article_id >= 0){
            savedArticleRepository.deleteById(saved_article_id);
            return true;
        }
        return false;
    }

    public List<SavedArticleMapper> getList(int profile_id){
        return savedArticleRepository.getAll(profile_id);
    }


}

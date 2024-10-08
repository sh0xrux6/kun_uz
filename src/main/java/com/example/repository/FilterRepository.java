package com.example.repository;
import com.example.Entity.CommentEntity;
import com.example.dto.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FilterRepository {
    @Autowired
    private EntityManager entityManager;


    public FilterResultDTO<ArticleDTO> filterArticle(ArticleDTO dto, int page, int size){
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if(dto.getTitle() != null){
            stringBuilder.append(" AND a.title LIKE :title");
            params.put("title", dto.getTitle());
        }
        if (dto.getContent()!=null){
            stringBuilder.append(" AND a.content LIKE :content");
            params.put("content", dto.getContent());
        }
        if (dto.getDescription()!=null){
            stringBuilder.append(" AND a.description LIKE :description");
            params.put("description", dto.getDescription());
        }
        if (dto.getRegion()!=null){
            stringBuilder.append(" AND a.region LIKE :region");
            params.put("region", dto.getRegion().getId());
        }
        if (dto.getCategory()!=null){
            stringBuilder.append(" AND a.category LIKE :category");
            params.put("category", dto.getCategory().getId());
        }
        if (dto.getCreatedDate()!=null){
            stringBuilder.append(" AND a.publishedDate >= :publishedDate");
            params.put("createdDate", dto.getPublishedDate());
        }

        StringBuilder selectBuilder = new StringBuilder("select a.uuid,a.title,a.description,a.region,a.category,a.published_date from ArticleEntity a where a.visible=true");
        selectBuilder.append(stringBuilder);
        selectBuilder.append("order by a.createdDate desc");

        StringBuilder countBuilder = new StringBuilder("select count(a) from ArticleEntity a where a.visible=true");
        countBuilder.append(stringBuilder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString(), ArticleDTO.class);
        selectQuery.setMaxResults(size); // limit
        selectQuery.setFirstResult(page*size); // offset

        Query countQuery = entityManager.createQuery(countBuilder.toString(), ArticleDTO.class);

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<ArticleDTO> entityList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResultDTO<>(entityList, totalCount);
    }

    public FilterResultDTO<CommentDTO> filterComment(CommentDTO dto, int page, int size){
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (dto.getProfileId()>=0){
            stringBuilder.append(" AND c.profile_id LIKE :profileId");
            params.put("profileId", dto.getProfileId());
        }
        if (dto.getContent().isBlank()){
            stringBuilder.append(" AND c.content LIKE :content");
            params.put("content", dto.getContent());
        }
        if (dto.getCreatedDate()!=null){
            stringBuilder.append(" AND c.created_date >= :createdDate");
            params.put("createdDate", dto.getCreatedDate());
        }
        if (dto.getArticleId()!=null){
            stringBuilder.append(" AND c.article_id LIKE :articleId");
            params.put("articleId", dto.getArticleId());
        }
        StringBuilder selectBuilder = new StringBuilder("select c from CommentEntity c where c.visible=true");
        selectBuilder.append(stringBuilder);
        selectBuilder.append("order by c.createdDate desc");

        StringBuilder countBuilder = new StringBuilder("select count(c) from CommentEntity c where c.visible=true");
        countBuilder.append(stringBuilder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size); // limit
        selectQuery.setFirstResult(size * page);

        Query countQuery = entityManager.createQuery(countBuilder.toString());
        // params
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<CommentDTO> entityList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResultDTO<>(entityList, totalCount);
    }

    //7. Filter (name,surname,phone,role,created_date_from,created_date_to)
    public FilterResultDTO<ProfileDTO> filterProfile(ProfileDTO dto, int page, int size){
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (dto.getName()!=null){
            stringBuilder.append(" AND p.name LIKE :name");
            params.put("name", dto.getName());
        }
        if (dto.getSurname()!=null){
            stringBuilder.append(" AND p.surname LIKE :surname");
            params.put("surname", dto.getSurname());
        }
        if (dto.getPhone()!=null){
            stringBuilder.append(" AND p.phone LIKE :phone");
            params.put("phone", dto.getPhone());
        }
        if (dto.getRole()!=null){
            stringBuilder.append(" AND p.role LIKE :role");
            params.put("role", dto.getRole());
        }
        if (dto.getCreatedDate()!=null){
            stringBuilder.append(" AND p.created_date <= :createdDate OR p.created_date >= :createdDate");
            params.put("createdDate", dto.getCreatedDate());
        }

        StringBuilder selectBuilder = new StringBuilder("select p from ProfileEntity p where p.visible=true");
        selectBuilder.append(stringBuilder);
        selectBuilder.append("order by p.createdDate desc");

        StringBuilder countBuilder = new StringBuilder("select count(p) from ProfileEntity p where p.visible=true");
        countBuilder.append(stringBuilder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult(size * page);

        Query countQuery = entityManager.createQuery(countBuilder.toString());
        // params
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<ProfileDTO> entityList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResultDTO<>(entityList, totalCount);

    }
}

package com.example.service;

import com.example.Entity.*;
import com.example.Enum.ArticleStatus;
import com.example.Enum.Language;
import com.example.dto.*;
import com.example.exp.AppBadRequestException;
import com.example.mapper.ArticleShortInfoMapper;
import com.example.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepo;
    @Autowired
    private ArticlesTypesService articlesTypes;
/*




    18. Filter Article (id,title,region_id,category_id,crated_date_from,created_date_to
        published_date_from,published_date_to,moderator_id,publisher_id,status) with Pagination (PUBLISHER)
        ArticleShortInfo
         */

    /*id(uuid),title,description,content,shared_count,image_id,
    region_id,category_id,moderator_id,publisher_id,status(Published,NotPublished)
    created_date,published_date,visible,view_count*/

    // 1. CREATE (Moderator) status(NotPublished) (title,description,content,shared_count,image_id, region_id,category_id)
    public ArticleDTO create(ArticleDTO dto,int prt) {
        isValid(dto);
        //(title,description,content,shared_count,image_id, region_id,category_id)
        ArticleEntity entity = toEntity(dto);

        ///Moderator id
        ProfileEntity moderator = new ProfileEntity();
        moderator.setId(prt);
        entity.setModerator(moderator);

        articleRepo.save(entity);
        articlesTypes.create(dto.getUuid(),dto.getArticleType());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private void isValid(ArticleDTO dto) {
        //(title,description,content,shared_count,image_id, region_id,category_id)
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new AppBadRequestException("Title is required");
        }
        else if(dto.getDescription()==null || dto.getDescription().isBlank()){
            throw new AppBadRequestException("Description is required");
        }
        else if(dto.getContent()==null || dto.getContent().isBlank()){
            throw new AppBadRequestException("Content is required");
        }
        else if(!dto.getRegion().isVisible()){
            throw new AppBadRequestException("Region is not visible");
        }
        else if (!dto.getCategory().isVisible()) {
            throw new AppBadRequestException("Category is not visible");
        }

    }

    /*
    * 2. Update (Moderator (status to not publish)) (remove old image)
        (title,description,content,shared_count,image_id, region_id,category_id)*/
    public boolean update(UUID id,ArticleDTO dto,int prt) {
        Optional<ArticleEntity> optional = articleRepo.findById(id);
        if(optional.isEmpty()) {
            throw new AppBadRequestException("Article not found");
        }

        ArticleEntity entity = optional.get();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setSharedCount(dto.getShared_count());
        entity.setImageId(dto.getImageId());
        entity.setUpdatedBy(prt);

        /// Region id
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(dto.getRegion().getId());
        entity.setRegion(regionEntity);
        ///Category id
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(dto.getCategory().getId());
        entity.setCategory(categoryEntity);
        articleRepo.save(entity);
        articlesTypes.merge(entity.getUuid(), dto.getArticleType());

        return true;
    }

    //3. Delete Article (MODERATOR)
    public boolean delete(UUID id) {
        if (articleRepo.existsById(id)) {
            articleRepo.deleteById(id);
            return true;
        }

        throw new AppBadRequestException("Article not found");
    }

    //4. Change status by id (PUBLISHER) (publish,not_publish)
    public boolean changeStatus(UUID id, int prt){
        Optional<ArticleEntity> optional = articleRepo.findById(id);
        if(optional.isEmpty()) {
            throw new AppBadRequestException("Article not found");
        }
        ArticleEntity entity = optional.get();

        if (entity.getStatus().equals(ArticleStatus.Published)){
            entity.setStatus(ArticleStatus.NotPublished);
        }

        else {
            entity.setStatus(ArticleStatus.Published);
            entity.setPublishedDate(LocalDateTime.now());
            ProfileEntity publisher = new ProfileEntity();
            publisher.setId(prt);
            entity.setPublisher(publisher);
        }

        articleRepo.save(entity);
        return true;
    }

    // 5. Get Last 5 Article By Types  ordered_by_created_date (ArticleShortInfo)
    public List<ArticleShortInfoMapper> getLastFive(int type){
        return articleRepo.getLastFive(type);
    }

    //6. Get Last 3 Article By Types  ordered_by_created_date (ArticleShortInfo)
    public List<ArticleShortInfoMapper> getLastThree(int type){
        return articleRepo.getLastThree(type);
    }

    //7. Get Last 8  Articles witch id not included in given list. ([1,2,3,]) ,(ArticleShortInfo)
    public List<ArticleShortInfoMapper> getLastEight(int id){
        return articleRepo.getLastEight(id);
    }

    //8. Get Article By id And Lang (ArticleFullInfo)
    public ArticleDTO getByIdAndLang(UUID id, Language lang){
        Optional<ArticleEntity> optional =articleRepo.findById(id);
        if(optional.isEmpty()) {
            throw new AppBadRequestException("Article not found");
        }
        ArticleEntity entity = optional.get();

        if (entity.getLang()==lang){
            ArticleDTO dto = toDto(entity);
            dto.setLang(lang);

            ///Moderator id
            ProfileDTO moderator = new ProfileDTO();
            moderator.setId(entity.getModerator().getId());
            dto.setModerator(moderator);

            ///Publisher id
            ProfileDTO publisher = new ProfileDTO();
            publisher.setId(entity.getPublisher().getId());
            dto.setPublisher(publisher);

            ////
            dto.setStatus(entity.getStatus());
            dto.setPublishedDate(entity.getPublishedDate());
            dto.setViewCount(entity.getViewCount());

            return dto;
        }
        throw new AppBadRequestException("Lang not found");
    }
//9. Get Last 4 Article By Types and except given article id.
//        ArticleShortInfo

        public List<ArticleShortInfoMapper> getFourArticlesByTypes(int type,int id){
        List<ArticleShortInfoMapper> list = articleRepo.getLastFourByTypeExcept(type,id);

        if (list.isEmpty()) {
            throw new AppBadRequestException("Article not found");
        }


        return list;
        }


    //10. Get 4 most read articles (ArticleShortInfo)
    public List<ArticleShortInfoMapper> getMostViewed(){
        return articleRepo.getMostViewed().stream().toList();
    }

    //12 Get Last 5 Article By Types  And By Region Key (ArticleShortInfo)
    public List<ArticleDTO> getByTypeAndRegion(int type, int region){
        List<ArticleEntity> entities = articleRepo.getByTypeAndRegion(type,region);
        List<ArticleDTO> dto = new LinkedList<>();
        entities.forEach(e->dto.add(toDto(e)));
        return dto;
    }

    //13  Get Article list by Region Key (Pagination) ,(ArticleShortInfo)
    public PageImpl<ArticleShortInfoMapper> getByRegionPagination(int region,int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdDate"));
        Page<ArticleShortInfoMapper> entities = articleRepo.findAllByRegionId(region,pageable);
        List<ArticleShortInfoMapper> dto = entities.stream().toList();
        return new PageImpl<>(dto,pageable,entities.getTotalElements());
    }

    //14 Get Last 5 Article Category Key (ArticleShortInfo)
    public List<ArticleShortInfoMapper> getByCategory(int categoryId){
        List<ArticleShortInfoMapper> entities = articleRepo.findAllByCategoryId(categoryId);
        return entities.stream().toList();
    }

    //15 Get Article By Category Key (Pagination) ,(ArticleShortInfo)
    public PageImpl<ArticleShortInfoMapper> getByCategoryPagination(int categoryId,int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdDate"));
        Page<ArticleShortInfoMapper> entities = articleRepo.findAllByCategoryId(categoryId,pageable);
        List<ArticleShortInfoMapper> dto = entities.stream().toList();
        return new PageImpl<>(dto,pageable,entities.getTotalElements());
    }

    //16. Increase Article View Count by Article Id
    public int increaseViewCountById(UUID id){
        return articleRepo.increaseViewCountById(id);
    }

    // 17. Increase Share View Count by Article Id (article_id)
    public int increaseShareCountById(UUID id){
        return articleRepo.increaseShareCount(id);
    }



    private ArticleDTO toDto(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setUuid(dto.getUuid());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setShared_count(entity.getSharedCount());
        dto.setImageId(entity.getImageId());

        RegionDTO region = new RegionDTO();
        region.setId(entity.getRegion().getId());
        dto.setRegion(region);

        CategoryDTO category = new CategoryDTO();
        category.setId(entity.getCategory().getId());
        dto.setCategory(category);

        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private ArticleEntity toEntity(ArticleDTO dto) {
        ArticleEntity entity = new ArticleEntity();

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setSharedCount(dto.getShared_count());
        entity.setImageId(dto.getImageId());

        /// Region id
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(dto.getRegion().getId());
        entity.setRegion(regionEntity);
        ///Category id
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(dto.getCategory().getId());
        entity.setCategory(categoryEntity);



        return entity;
    }
}

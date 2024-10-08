package com.example.service;
import com.example.Entity.ArticleEntity;
import com.example.dto.*;
import com.example.repository.FilterRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class FilterService {
    @Autowired
    private FilterRepository filterRepository;

    public PageImpl<ArticleDTO> filterArticle(ArticleDTO filterDTO, int page, int size) {
        FilterResultDTO<ArticleDTO> result = filterRepository.filterArticle(filterDTO, page, size);
        List<ArticleDTO> list = result.getItems().stream().toList();
        return new PageImpl<>(list, PageRequest.of(page, size), result.getTotalCount());
    }

    private ArticleDTO toDTO(ArticleEntity articleEntity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setTitle(articleEntity.getTitle());
        dto.setDescription(articleEntity.getDescription());
        dto.setContent(articleEntity.getContent());

        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(articleEntity.getRegionId());
        dto.setRegion(regionDTO);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(articleEntity.getCategoryId());
        dto.setCategory(categoryDTO);

        return dto;
    }

    public PageImpl<CommentDTO> filterComment(CommentDTO dto, int page, int size) {
        FilterResultDTO<CommentDTO> result = filterRepository.filterComment(dto, page, size);
        List<CommentDTO> list = result.getItems().stream().toList();
        return new PageImpl<>(list, PageRequest.of(page, size), result.getTotalCount());
    }

    public PageImpl<ProfileDTO> filterProfile(ProfileDTO dto, int page, int size) {
        FilterResultDTO<ProfileDTO> result = filterRepository.filterProfile(dto, page, size);
        List<ProfileDTO> list = result.getItems().stream().toList();
        return new PageImpl<>(list, PageRequest.of(page, size), result.getTotalCount());
    }




}

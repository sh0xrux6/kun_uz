package com.example.dto;

import com.example.Entity.ArticleEntity;
import com.example.Entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class SavedArticleDTO {
    private Integer id;
    private UUID articleId;
    private int profileId;

}

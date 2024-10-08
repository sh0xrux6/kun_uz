package com.example.dto;

import com.example.Enum.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleLikeDTO {
    private int id;
    private UUID articleId;
    private int profileId;
    private ArticleStatus status;
    private LocalDateTime createdDate;

}

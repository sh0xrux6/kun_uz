package com.example.dto;

import com.example.Enum.ArticleStatus;
import com.example.Enum.Language;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    /*id(uuid),title,description,content,shared_count,image_id,
    region_id,category_id,moderator_id,publisher_id,status(Published,NotPublished)
    created_date,published_date,visible,view_count
    (Bitta article bir-nechta type da bo'lishi mumkun. Masalan Asosiy,Muharrir da.)*/


    private UUID uuid;

    @NotBlank
    @Size(min = 1, max = 255)
    private String title;

    @NotBlank
    @Size(min = 1, max = 255)
    private String description;

    @Size(min = 1)
    private String content;

    private int shared_count;
    private String imageId;
    private RegionDTO region;
    private CategoryDTO category;
    private ProfileDTO publisher;
    private ProfileDTO moderator;
    private List<Integer> articleType;
    private ArticleStatus status;

    @NotBlank
    private Language lang;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private long viewCount;

}

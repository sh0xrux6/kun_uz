package com.example.mapper;

import java.time.LocalDateTime;

public interface ArticleShortInfoMapper {
    //id(uuid),title,description,image(id,url),published_date
    int getId();
    String getTitle();
    String getDescription();
    int getImage();
    LocalDateTime getPublishedDate();

}

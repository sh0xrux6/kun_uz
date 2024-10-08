package com.example.mapper;

import java.util.UUID;

public interface SavedArticleMapper {

    int getId();
    int getArticleId();
    String getArticleTitle();
    String getArticleDescription();
    UUID getArticleImageId();
    String getUrl();

}

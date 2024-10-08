package com.example.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "saved_article")
public class SavedArticleEntity extends BaseEntity {

    @Column(name = "article_id",nullable = false)
    private UUID articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable=false, updatable=false)
    private ArticleEntity article;

    @Column(name = "profile_id",nullable = false)
    private int profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;

}

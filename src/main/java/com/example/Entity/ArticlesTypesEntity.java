package com.example.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "articles_types")
public class ArticlesTypesEntity extends BaseEntity {

    @Column(name = "article_id",nullable = false)
    private UUID articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable=false, updatable=false)
    private ArticleEntity article;

    @Column(name = "article_type_id")
    private Integer articleTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_type_id", insertable=false, updatable=false)
    private ArticleTypeEntity articleType;
}

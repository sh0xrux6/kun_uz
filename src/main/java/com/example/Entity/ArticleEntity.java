package com.example.Entity;
import com.example.Enum.ArticleStatus;
import com.example.Enum.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "article")
public class ArticleEntity extends BaseStringEntity{


    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "image_id")
    private String imageId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable=false, updatable=false)
    private AttachEntity image;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private ArticleStatus status = ArticleStatus.NotPublished;

    @Column(name = "published_date", nullable = false)
    private LocalDateTime publishedDate;

    @Column(name = "language")
    private Language lang;

    @Column(name = "view_count")
    private long viewCount;

    @Column(name = "shared_count")
    private int sharedCount ;

    @Column(name = "region_id")
    private int regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @Column(name = "category_id")
    private int categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "moderator_id",nullable = false)
    private int moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id",insertable = false, updatable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private int publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id",insertable = false,updatable = false)
    private ProfileEntity publisher;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<ArticleTypeEntity> articleTypeSet;


}

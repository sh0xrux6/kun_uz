package com.example.Entity;


import com.example.Enum.ArticleStatus;
import com.example.Enum.StatusLikeDis;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "article_like")
public class ArticleLikeEntity extends BaseEntity {

    @Column(name = "article_id")
    private UUID articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id",insertable=false, updatable=false)
    private ArticleEntity article;

    @Column(name = "profile_id")
    private int profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",insertable=false, updatable=false)
    private ProfileEntity profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusLikeDis status;

}

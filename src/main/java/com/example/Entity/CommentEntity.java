package com.example.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Column(name = "updates_date")
    private LocalDateTime updatedDate;

    @Column(name = "profile_id")
    private int profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable=false, updatable=false)
    private ProfileEntity profile;

    @Column(name = "content")
    private String content;

    @Column(name = "article_id")
    private UUID articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id",insertable=false, updatable=false)
    private ArticleEntity article;

    @Column(name = "reply_id")
    private int replyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id",insertable=false, updatable=false)
    private CommentEntity reply;

    @Column(name = "comment_like")
    private long commentLikes;

    @Column(name = "comment_dislike")
    private long dislikeCount;

}

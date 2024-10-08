package com.example.Entity;

import com.example.Enum.StatusLikeDis;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "commentLike")
public class CommentLikeEntity extends BaseEntity{

    @Column(name = "profile_id")
    private int profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "comment_id")
    private int commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id",insertable = false, updatable = false)
    private CommentEntity comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusLikeDis status;


}

package com.example.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CommentListPagination {
    //        (id,created_date,update_date,profile(id,name,surname),content,article(id,title),reply_id,)
    int getId();
    LocalDateTime getCreatedDate();
    LocalDateTime getUpdatesDate();
    int getProfileId();
    String getName();
    String getSurname();
    String getContent();
    UUID articleId();
    String title();
    int replyId();
}

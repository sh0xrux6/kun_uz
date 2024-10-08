package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    //id,created_date,update_date,profile_id,content,article_id,reply_id,visible
    private int id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private int profileId;
    private String content;
    private UUID articleId;
    private int replyId;
    private boolean visible;


}

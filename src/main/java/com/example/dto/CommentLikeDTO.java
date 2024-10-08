package com.example.dto;

import com.example.Enum.StatusLikeDis;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentLikeDTO {
    //    id,profile_id,comment_id,created_date,status,
    private int id;
    private int profileId;
    private int commentId;
    private LocalDateTime createdDate;
    private StatusLikeDis status;

}

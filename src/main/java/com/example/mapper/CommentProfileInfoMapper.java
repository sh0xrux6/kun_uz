package com.example.mapper;

import java.time.LocalDateTime;

public interface CommentProfileInfoMapper {
    //select c.id,c.created_date,c.updates_date,c.profile_id from comment as c
    int getId();
    LocalDateTime getCreatedDate();
    LocalDateTime getUpdatesDate();
    int getProfileId();
    String getName();
    String getSurname();

}

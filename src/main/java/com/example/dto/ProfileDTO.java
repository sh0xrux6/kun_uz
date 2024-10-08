package com.example.dto;

import com.example.Enum.ProfileRole;
import com.example.Enum.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class   ProfileDTO {
    //id,name,surname,email,phone,password,status,role,visible,created_date,photo_id
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
    private Boolean visible;
    private LocalDateTime createdDate;
    private String photoId;
    private String jwt;

}

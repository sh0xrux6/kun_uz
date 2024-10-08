package com.example.Entity;

import com.example.Enum.ProfileRole;
import com.example.Enum.ProfileStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Setter
@Getter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity{

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "surname",nullable = false)
    private String surname;

    @Email
    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "phone",nullable = false,unique = true)
    private String phone;

    @Column(name = "password",nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private ProfileStatus status ;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private ProfileRole role = ProfileRole.USER;



    @Column(name = "photo_id")
    private String photoId;

    @Column(name = "prt_id")
    private Integer prtId;


    @OneToMany(fetch = FetchType.LAZY)
    private Set<SavedArticleEntity> savedArticleSet;

}

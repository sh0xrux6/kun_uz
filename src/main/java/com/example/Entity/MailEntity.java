package com.example.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mail_history")
@Setter
@Getter
public class MailEntity {

    /*(id, email,message,created_date)*/
    @Id
    private int id;

    @Email
    @Column(name = "mail",nullable = false,unique = true)
    private String mail;

    @Column(name = "message",nullable = false)
    private String message;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}

package com.example.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "articleType")
@Setter
@Getter
public class ArticleTypeEntity extends BaseEntity{

    @Column(name = "order_num")
    private int orderNum;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "prt_id")
    private Integer prtId;
}

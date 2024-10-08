package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleTypeDTO {
    private int id;
    private int orderNumber;
    private String name;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private boolean visible;
    private LocalDateTime createdDate;
    private Integer prtId;

    public ArticleTypeDTO(int id, int orderNumber, String name) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.name = name;
    }

    public ArticleTypeDTO() {
    }
}

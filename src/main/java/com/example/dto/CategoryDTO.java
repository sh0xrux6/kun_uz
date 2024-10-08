package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    // id,order_number,name_uz, name_ru, name_en,visible,created_date
    private int id;
    private int orderNum;
    private String name;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private boolean visible;
    private LocalDateTime createdDate;
    private Integer prtId;

    public CategoryDTO(int id, int orderNum, String name) {
        this.id = id;
        this.orderNum = orderNum;
        this.name = name;
    }

    public CategoryDTO() {
    }
}

package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {
    //id,order_number,name_uz, name_ru, name_en,visible,created_date
    private int id;
    private int orderNumber;
    private String name;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private boolean visible;
    private LocalDateTime createdDate;
    private Integer prtId;

    public RegionDTO(int id, int orderNumber,String name) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.name = name;
    }

    public RegionDTO() {
    }
}

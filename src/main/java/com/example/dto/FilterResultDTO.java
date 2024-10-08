package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FilterResultDTO<T>{

    private List<T> items; // List of filtered entities
    private long totalCount; // Total count of matching entities

    public FilterResultDTO(List<T> items, long totalCount) {
        this.items = items;
        this.totalCount = totalCount;

    }

}

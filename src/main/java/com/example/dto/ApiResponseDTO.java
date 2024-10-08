package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponseDTO {
    private boolean status;
    private String message;
    private Object data;

    public ApiResponseDTO(boolean status,String message){
        this.status = status;
        this.message = message;
    }
    public ApiResponseDTO(boolean status,Object data){
        this.status = status;
        this.data = data;
    }

    public ApiResponseDTO() {

    }
}

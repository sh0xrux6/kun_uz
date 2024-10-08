package com.example.dto;

import com.example.Enum.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtDTO {
    private Integer id;
    private ProfileRole role;

    public JwtDTO(Integer id, ProfileRole role) {
        this.id = id;
        this.role = role;
    }
}

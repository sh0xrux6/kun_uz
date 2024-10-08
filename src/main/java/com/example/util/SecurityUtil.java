package com.example.util;

import com.example.Enum.ProfileRole;
import com.example.dto.JwtDTO;
import com.example.exp.AppMethodNotAllowedException;
import com.example.exp.UnAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtil {

    public static JwtDTO hasRole(String authToken, ProfileRole role){
        JwtDTO jwtDTO = getJwtDTO(authToken);
        if (!jwtDTO.getRole().equals(role)){
            throw new AppMethodNotAllowedException("not allowed");
        }
        return jwtDTO;
    }

    public static JwtDTO hasRole(String authToken, ProfileRole... roles){
        JwtDTO jwtDTO = getJwtDTO(authToken);
        boolean found = false;
        for (ProfileRole role: roles){
            if (jwtDTO.getRole().equals(role)){
                found=true;
            }
        }
        if(!found){
            throw new AppMethodNotAllowedException("not allowed");
        }
        return jwtDTO;
    }


    public static JwtDTO hasRole(HttpServletRequest request, ProfileRole... roles){
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");
        if(roles==null){
            return new JwtDTO(id, role);
        }
        boolean found = false;
        for (ProfileRole r: roles){
            if (role.equals(r)){
                found=true;
            }
        }
        if(!found){
            throw new AppMethodNotAllowedException("not allowed");
        }
        return new JwtDTO(id, role);
    }

    public static JwtDTO getJwtDTO(String authToken){
        if(authToken.startsWith("Bearer ")){
            String jwt = authToken.substring(7);
            return JWTUtil.decode(jwt);
        }
        throw new UnAuthorizedException("Not authorised");
    }
}

package com.example.config;

import com.example.dto.JwtDTO;
import com.example.util.JWTUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends GenericFilterBean {


    public static final Map<String, String> openURL = new HashMap<>();

    static {
        openURL.put("auth/login", "auth/login");

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(openURL.containsKey(request.getRequestURI())){
            filterChain.doFilter(request,response);
        }
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Message", "Token Not Found.");
            return;
        }

        String token = authHeader.substring(7);
        JwtDTO jwtDto;
        try {
            jwtDto = JWTUtil.decode(token);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Message", "Token Not Valid");
            return;
        }
        request.setAttribute("id", jwtDto.getId());
        request.setAttribute("role", jwtDto.getRole());
        System.out.println(jwtDto.getRole());
        filterChain.doFilter(request, response);
    }
}

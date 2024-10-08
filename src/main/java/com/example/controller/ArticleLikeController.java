package com.example.controller;

import com.example.Enum.ProfileRole;
import com.example.dto.JwtDTO;
import com.example.service.ArticleLikeService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/article_like")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/like")
    public ResponseEntity<?> like(String articleId, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR,ProfileRole.USER,ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleLikeService.like(UUID.fromString(articleId), jwtDTO.getId()));
    }

    @PostMapping("/dislike")
    public ResponseEntity<?> dislike(String articleId, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR,ProfileRole.USER,ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleLikeService.dislike(UUID.fromString(articleId), jwtDTO.getId()));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(String articleId, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR,ProfileRole.USER,ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleLikeService.remove(UUID.fromString(articleId), jwtDTO.getId()));
    }
}

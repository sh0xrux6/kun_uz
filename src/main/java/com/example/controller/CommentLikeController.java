package com.example.controller;

import com.example.Enum.ProfileRole;
import com.example.dto.JwtDTO;
import com.example.service.CommentLikeService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commentLike")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping("/like")
    public ResponseEntity<Boolean> like(HttpServletRequest  request,@RequestParam int commentId) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR,ProfileRole.PUBLISHER,ProfileRole.USER);
        return ResponseEntity.ok(commentLikeService.like(commentId, jwtDTO.getId()));
    }

    @PostMapping("dislike")
    public ResponseEntity<Boolean> dislike(HttpServletRequest  request,@RequestParam int commentId) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR,ProfileRole.PUBLISHER,ProfileRole.USER);
        return ResponseEntity.ok(commentLikeService.dislike(commentId, jwtDTO.getId()));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Boolean> remove(int commentId,HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR,ProfileRole.PUBLISHER,ProfileRole.USER);
        return ResponseEntity.ok(commentLikeService.remove(commentId,jwtDTO.getId()));
    }


}

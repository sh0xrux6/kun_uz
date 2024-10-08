package com.example.controller;

import com.example.Enum.ProfileRole;
import com.example.dto.JwtDTO;
import com.example.dto.SavedArticleDTO;
import com.example.mapper.SavedArticleMapper;
import com.example.service.SavedArticleService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/saved_article")
public class SavedArticleController {
    @Autowired
    private SavedArticleService savedArticleService;


    @PostMapping("/create")
    public ResponseEntity<Boolean> create(HttpServletRequest request,@RequestParam("article_uuid") String article_uuid ){
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR,ProfileRole.USER,ProfileRole.PUBLISHER);
        return ResponseEntity.ok(savedArticleService.create(jwtDTO.getId(), UUID.fromString(article_uuid)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id){
        return ResponseEntity.ok(savedArticleService.delete(id));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<SavedArticleMapper>> get(@PathVariable("id") int id){
        return ResponseEntity.ok(savedArticleService.getList(id));
    }
}

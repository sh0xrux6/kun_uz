package com.example.controller;


import com.example.Enum.Language;
import com.example.Enum.ProfileRole;
import com.example.dto.ArticleDTO;
import com.example.dto.JwtDTO;
import com.example.mapper.ArticleShortInfoMapper;
import com.example.service.ArticleService;
import com.example.service.FilterService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private FilterService filterService;

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/create")
    public ResponseEntity<ArticleDTO> create(@Valid @RequestBody ArticleDTO article, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(article, jwtDTO.getId()));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable String id,@RequestBody ArticleDTO article, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(UUID.fromString(id),article, jwtDTO.getId()));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String id, HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.delete(UUID.fromString(id)));
    }

    @PreAuthorize("hasAnyRole('PUBLISHER')")
    @PutMapping("/update/status{id}")
    public ResponseEntity<Boolean> updateStatus(@PathVariable String id, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatus(UUID.fromString(id), jwtDTO.getId()));
    }

    @GetMapping("/last/five")
    public ResponseEntity<List<ArticleShortInfoMapper>> getLastFiveByType(int type) {
        return ResponseEntity.ok(articleService.getLastFive(type));
    }

    @GetMapping("/last/three")
    public ResponseEntity<List<ArticleShortInfoMapper>> getLastThreeByType(int type) {
        return ResponseEntity.ok(articleService.getLastThree(type));
    }

    @GetMapping("/last/eight")
    public ResponseEntity<List<ArticleShortInfoMapper>> getLastEightByType(int id) {
        return ResponseEntity.ok(articleService.getLastEight(id));
    }

    @GetMapping("/byIdAndLang/{id}")
    public ResponseEntity<ArticleDTO> getByIdAndLang(@PathVariable String id, @RequestParam Language lang) {
        return ResponseEntity.ok(articleService.getByIdAndLang(UUID.fromString(id),lang));
    }

    @GetMapping("last/four/except")
    public ResponseEntity<List<ArticleShortInfoMapper>> getLastFourByTypeExcept(int type,int id) {
        return ResponseEntity.ok(articleService.getFourArticlesByTypes(type,id));
    }

    @GetMapping("/mostViewed")
    public ResponseEntity<List<ArticleShortInfoMapper>> getMostRead(){
        return ResponseEntity.ok(articleService.getMostViewed());
    }

    @GetMapping("/last/fiveByTypeAndRegion")
    public ResponseEntity<List<ArticleDTO>> getLastFive(@RequestParam int type,@RequestParam int region){
        return ResponseEntity.ok(articleService.getByTypeAndRegion(type,region));
    }

    @GetMapping("/listByRegionPagination")
    public ResponseEntity<PageImpl<ArticleShortInfoMapper>> getListByRegion(@RequestParam int region,@RequestParam int page,@RequestParam int size) {
        return ResponseEntity.ok(articleService.getByRegionPagination(region,page,size));
    }

    @GetMapping("/last/fiveByCategory")
    public ResponseEntity<List<ArticleShortInfoMapper>> getLastFiveByCategory(@RequestParam int category){
        return ResponseEntity.ok(articleService.getByCategory(category));
    }

    @GetMapping("/listByCategoryPagination")
    public ResponseEntity<PageImpl<ArticleShortInfoMapper>> getListByCategory(@RequestParam int category,@RequestParam int page,@RequestParam int size) {
        return ResponseEntity.ok(articleService.getByCategoryPagination(category,page,size));
    }

    @PutMapping("increase/viewCount/{id}")
    public ResponseEntity<?> increaseViewCount(@PathVariable String id) {
        return ResponseEntity.ok(articleService.increaseViewCountById(UUID.fromString(id)));
    }

    @PutMapping("/increase/shareCount/{id}")
    public ResponseEntity<?> increaseShareCount(@PathVariable String id){
        return ResponseEntity.ok(articleService.increaseShareCountById(UUID.fromString(id)));
    }

    @GetMapping("/getPagination/filter")
    public ResponseEntity<?> getByFilter(@RequestBody ArticleDTO dto,@RequestParam int page,@RequestParam int size) {
        return ResponseEntity.ok(filterService.filterArticle(dto,page,size));
    }













}

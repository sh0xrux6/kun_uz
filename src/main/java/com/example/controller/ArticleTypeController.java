package com.example.controller;

import com.example.Enum.Language;
import com.example.Enum.ProfileRole;
import com.example.dto.ArticleTypeDTO;
import com.example.dto.JwtDTO;
import com.example.service.ArticleTypeService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ArticleTypeDTO> create(HttpServletRequest request, @RequestBody ArticleTypeDTO dto) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleTypeService.create(dto,jwtDTO.getId()));
    }

    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    @PutMapping("update/{id}")
    public ResponseEntity<Boolean> update(HttpServletRequest request,@PathVariable int id, @RequestBody ArticleTypeDTO dto) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleTypeService.update(id, dto,jwtDTO.getId()));
    }

    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(HttpServletRequest request,@PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleTypeService.delete(id));
    }

    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<ArticleTypeDTO>> getAll(HttpServletRequest request) {
        return ResponseEntity.ok(articleTypeService.getAll());
    }

    @GetMapping("/getByLanguage")
    public ResponseEntity<List<ArticleTypeDTO>> getAllByLang(@RequestParam Language lang) {
        return ResponseEntity.ok(articleTypeService.getByLangV2(lang));
    }
}

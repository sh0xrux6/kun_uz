package com.example.controller;

import com.example.Enum.Language;
import com.example.Enum.ProfileRole;
import com.example.dto.CategoryDTO;

import com.example.dto.JwtDTO;
import com.example.service.CategoryService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(HttpServletRequest request, @RequestBody CategoryDTO dto) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(categoryService.create(dto, jwtDTO.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/{id}")
    public ResponseEntity<Boolean> update(HttpServletRequest request,@PathVariable int id, @RequestBody CategoryDTO dto) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(categoryService.update(id, dto, jwtDTO.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(HttpServletRequest request,@PathVariable("id") Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> getAll(HttpServletRequest request) {
        return ResponseEntity.ok(categoryService.getList());
    }

    @GetMapping("/getByLanguage")
    public ResponseEntity<List<CategoryDTO>> getAllByLang(@RequestParam Language lang) {
        return ResponseEntity.ok(categoryService.getByLangV2(lang));
    }

}

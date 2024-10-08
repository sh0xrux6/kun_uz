package com.example.controller;

import com.example.Enum.Language;
import com.example.Enum.ProfileRole;
import com.example.dto.JwtDTO;
import com.example.dto.RegionDTO;
import com.example.service.RegionService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<RegionDTO> create(HttpServletRequest request, @RequestBody RegionDTO dto) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(regionService.create(dto,jwtDTO.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/{id}")
    public ResponseEntity<Boolean> update(HttpServletRequest request,@PathVariable int id, @RequestBody RegionDTO dto) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(regionService.update(id, dto,jwtDTO.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(HttpServletRequest request,@PathVariable("id") Integer id) {
        return ResponseEntity.ok(regionService.delete(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<RegionDTO>> getAll(HttpServletRequest request) {
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping("/getByLanguage")
    public ResponseEntity<List<RegionDTO>> getAllByLang(@RequestParam Language lang) {
        return ResponseEntity.ok(regionService.getByLangV2(lang));
    }

}

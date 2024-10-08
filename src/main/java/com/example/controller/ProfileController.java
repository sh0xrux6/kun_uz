package com.example.controller;

import com.example.Enum.ProfileRole;
import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.service.FilterService;
import com.example.service.ProfileService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private FilterService filterService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(HttpServletRequest request,@RequestBody ProfileDTO profileDTO) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request,ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(profileService.create(profileDTO,jwtDTO.getId()));

    }
    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    @PutMapping("/update/admin/{id}")
    public ResponseEntity<ProfileDTO> updateAdmin(HttpServletRequest request, @PathVariable int id, @RequestBody ProfileDTO profileDTO) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request,ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(profileService.updateByAdmin(id,profileDTO,jwtDTO.getId()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable int id,@RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.update(id,profileDTO));
    }

    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    @GetMapping("/pagination/admin")
    public ResponseEntity<?> getPagination(@RequestParam int page,@RequestParam int size) {
        return ResponseEntity.ok(profileService.getByPagination(page,size));
    }

    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return ResponseEntity.ok(profileService.delete(id));
    }

    @PutMapping("/update/image/{id}")
    public ResponseEntity<Boolean> updateImage(@PathVariable int id,String photoId) {
        return ResponseEntity.ok(profileService.updatePhoto(id,photoId));
    }

    @GetMapping("/getByPagination")
    public ResponseEntity<?> getByPagination(@RequestBody ProfileDTO dto,@RequestParam int page,@RequestParam int size) {
        return ResponseEntity.ok(filterService.filterProfile(dto,page,size));
    }
}

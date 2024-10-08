package com.example.controller;

import com.example.Entity.AttachEntity;
import com.example.dto.AttachDTO;
import com.example.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    //1.Upload  (ANY)
    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(attachService.save(file));
    }

    //2. Open (by id)
    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        return attachService.loadImage(fileName);
    }


    //3. Open general (by id)
    @GetMapping(value = "/open/general/{id}", produces = MediaType.ALL_VALUE)
    public byte[] openByGeneral(@PathVariable("id") String id) {
        return attachService.openGeneral(id);
    }

    //4.Download (by id  with origin name)
    @PostMapping("/download")
    public ResponseEntity<String> download(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(attachService.saveToSystem(file));
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getPagination")
    public ResponseEntity<PageImpl<AttachEntity>> getPagination(@RequestParam("page") int page,@RequestParam("size")int size) {
        return ResponseEntity.ok(attachService.getByPagination(page, size));
    }

    //6. Delete by id (delete from system and table) (ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        return ResponseEntity.ok(attachService.delete(id));
    }
}

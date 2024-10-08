package com.example.controller;

import com.example.Entity.MailEntity;
import com.example.service.MailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/mailHistory")
public class MailHistoryController {

    @Autowired
    private MailHistoryService mailHistoryService;

    @GetMapping("/get/history")
    public ResponseEntity<List<MailEntity>> getMailHistory(@RequestParam String mail) {
        return ResponseEntity.ok(mailHistoryService.getHistory(mail));
    }

    @GetMapping("/get/history/ByDate")
    public ResponseEntity<List<MailEntity>> getMailHistoryByDate(@RequestParam String mail,@RequestParam LocalDateTime date) {
        return ResponseEntity.ok(mailHistoryService.getByDateAndMail(mail,date));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/byPagination")
    public ResponseEntity<PageImpl<MailEntity>> getPagination(@RequestParam int page, int size) {
        return ResponseEntity.ok(mailHistoryService.getMailByPagination(page,size));
    }




}

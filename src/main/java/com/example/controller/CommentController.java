package com.example.controller;

import com.example.Enum.ProfileRole;
import com.example.dto.CommentDTO;
import com.example.dto.JwtDTO;
import com.example.mapper.CommentProfileInfoMapper;
import com.example.mapper.CommentListPagination;
import com.example.service.CommentService;
import com.example.service.FilterService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private FilterService filterService;



    @PostMapping("/create")
    public ResponseEntity<CommentDTO> create(CommentDTO comment, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR,ProfileRole.USER,ProfileRole.PUBLISHER);
        return ResponseEntity.ok(commentService.create(comment, jwtDTO.getId()));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> update(int id, CommentDTO comment, HttpServletRequest request){
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR,ProfileRole.USER,ProfileRole.PUBLISHER);
        return ResponseEntity.ok(commentService.update(id, comment,jwtDTO.getId()));
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id, HttpServletRequest request){
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN, ProfileRole.MODERATOR,ProfileRole.USER,ProfileRole.PUBLISHER);
        return ResponseEntity.ok(commentService.delete(id,jwtDTO.getId()));
    }

    @GetMapping("/list/article_id")
    public ResponseEntity<List<CommentProfileInfoMapper>> ListByArticleId(@RequestParam("id") UUID id){
        return ResponseEntity.ok(commentService.getListByArticleId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/pagination")
    public ResponseEntity<PageImpl<CommentListPagination>> getListByPagination(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        return ResponseEntity.ok(commentService.getCommentPagination(page,size));
    }

    @GetMapping("/list/repliedComment")
    public ResponseEntity<List<CommentProfileInfoMapper>> getRelyIdList(@RequestParam("id") Integer id){
        return ResponseEntity.ok(commentService.getReplyListByCommentId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("getPagination")
    public ResponseEntity<?> getCommentFilter(@RequestBody CommentDTO comment,@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        return ResponseEntity.ok(filterService.filterComment(comment,page,size));
    }








}

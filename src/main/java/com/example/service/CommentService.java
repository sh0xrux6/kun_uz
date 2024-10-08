package com.example.service;

import com.example.Entity.CommentEntity;
import com.example.Entity.ProfileEntity;
import com.example.Enum.ProfileRole;
import com.example.dto.CommentDTO;
import com.example.exp.AppBadRequestException;
import com.example.mapper.CommentProfileInfoMapper;
import com.example.mapper.CommentListPagination;
import com.example.repository.CommentRepository;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProfileRepository profileRepository;
/*


    6. Comment Filter(id,created_date_from,created_date_to,profile_id,article_id) with Pagination (ADMIN)
        id,created_date,update_date,profile_id,content,article_id,reply_id,visible

        */



    //1. CREATE (ANY)(content,article_id,reply_id)
    public CommentDTO create(CommentDTO dto,int prtId) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setReplyId(dto.getReplyId());
        entity.setProfileId(prtId);
        commentRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    //    2. UPDATE (ANY and owner) (content,article_id)
    public boolean update(int id,CommentDTO dto,int prt_id) {
        if (!commentRepository.existsById(id)) {
            throw new AppBadRequestException("comment not found");
        }
        if (dto.getProfileId()!=prt_id){
            throw new AppBadRequestException("profileId not match");
        }
        CommentEntity entity = commentRepository.findById(id).get();
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setUpdatedDate(LocalDateTime.now());
        commentRepository.save(entity);
        return true;
    }

    //    3. DELETE (ADMIN,ANY(only owner))
    public boolean delete(int id,int prt_id) {
        CommentEntity entity = commentRepository.findById(id).get();
        ProfileEntity profile = profileRepository.findById(prt_id).get();

        if (entity.getProfileId()==prt_id || profile.getRole()== ProfileRole.ADMIN) {
            if (commentRepository.existsById(id)) {
                commentRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    //    4. Get Article Comment List By Article_id  id,created_date,update_date,profile(id,name,surname)
    public List<CommentProfileInfoMapper> getListByArticleId(UUID articleId) {
        return commentRepository.getByArticleId(articleId);
    }

    //5. Comment List (pagination) (ADMIN)
    public PageImpl<CommentListPagination> getCommentPagination(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdDate"));
        Page<CommentListPagination> pages = commentRepository.getCommentListPagination(pageable);
        List<CommentListPagination> list = pages.stream().toList();
        return new PageImpl<>(list,pageable,pages.getTotalElements());
    }


    //7. Get Replied Comment List by Comment Id id,created_date,update_date,profile(id,name,surname)
    public List<CommentProfileInfoMapper> getReplyListByCommentId(int commentId) {
        return commentRepository.getRepliedCommentList(commentId);
    }


    public CommentDTO get(int id) {
        CommentEntity entity = commentRepository.getAllById(id).get();
        return toDto(entity);
    }

    public CommentDTO toDto(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setArticleId(entity.getArticleId());
        dto.setReplyId(entity.getReplyId());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }
}

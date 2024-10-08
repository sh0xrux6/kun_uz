package com.example.service;

import com.example.Entity.MailEntity;
import com.example.repository.MailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MailHistoryService {
    @Autowired
    private MailHistoryRepository mailRepository;
    /*
    public PageImpl<ProfileDTO> getByPagination(int page, int size){

        Page<ProfileEntity> pageEntity = profileRepository.findAll(pageable);
        List<ProfileEntity> entities = pageEntity.getContent();
        List<ProfileDTO> dtos = new ArrayList<>();

        entities.forEach(e->dtos.add(toDto(e)));
        return new PageImpl<>(dtos,pageable,pageEntity.getTotalElements());
    }
    4. Pagination (ADMIN)
            (id, email,message,created_date)

       (!Should be limit for email sending. For 1 email 4 sms allowed during 1 minut. )*/

    public List<MailEntity> getHistory(String mail){
        return mailRepository.findByMail(mail);
    }

    public List<MailEntity> getByDateAndMail(String mail,LocalDateTime date){
        LocalDateTime localDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
        return mailRepository.findByMailAndCreatedDate(mail,localDateTime);
    }

    public PageImpl<MailEntity> getMailByPagination(int page, int size){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page,size,sort);
        List<MailEntity> entities = mailRepository.findAll(pageable).getContent();

        return new PageImpl<>(entities, pageable, entities.size());

    }



}

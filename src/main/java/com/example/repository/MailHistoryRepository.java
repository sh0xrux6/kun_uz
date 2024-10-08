package com.example.repository;

import com.example.Entity.MailEntity;
import com.example.Entity.ProfileEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MailHistoryRepository extends CrudRepository<MailEntity,Integer>, PagingAndSortingRepository<MailEntity,Integer> {

    List<MailEntity> findByMail(String mail);

    List<MailEntity> findByMailAndCreatedDate(String mail,LocalDateTime date);



}

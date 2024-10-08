package com.example.service;

import com.example.Enum.ProfileRole;
import com.example.dto.ProfileDTO;
import com.example.Entity.ProfileEntity;
import com.example.Enum.ProfileStatus;
import com.example.exp.AppBadRequestException;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;



    public ProfileDTO create(ProfileDTO profile,Integer prtId){
        Optional<ProfileEntity> profileByEmail = profileRepository.findByEmail(profile.getEmail());
        if(profileByEmail.isPresent()){
            throw new AppBadRequestException("Email already exists");
        }

        Optional<ProfileEntity> profileByPhone = profileRepository.findByPhone(profile.getPhone());
        if(profileByPhone.isPresent()){
            throw new AppBadRequestException("Phone already exists");
        }
        ProfileEntity isAdmin = profileRepository.findById(prtId).get();

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(profile.getName());
        profileEntity.setSurname(profile.getSurname());
        profileEntity.setPhone(profile.getPhone());
        profileEntity.setEmail(profile.getEmail());
        profileEntity.setPassword(MD5Util.encode(profile.getPassword()));
        profileEntity.setStatus(ProfileStatus.ACTIVE);
        profileEntity.setRole(ProfileRole.USER);
        if(isAdmin.getRole().equals(ProfileRole.ADMIN)){
            if (profile.getRole()==ProfileRole.MODERATOR || profile.getRole()==ProfileRole.PUBLISHER) {
                profileEntity.setRole(profile.getRole());
            }
        }
        profileEntity.setPhotoId(profile.getPhotoId());
        profileEntity.setPrtId(prtId);
        profileRepository.save(profileEntity);

        profile.setId(profileEntity.getId());
        profile.setCreatedDate(profileEntity.getCreatedDate());
        return profile;
    }

    public ProfileDTO updateByAdmin(int id,ProfileDTO profile,int prt){
        Optional<ProfileEntity> optional = profileRepository.findById(profile.getId());
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Profile not found");
        }
        ProfileEntity profileEntity = optional.get();
        profileEntity.setStatus(profile.getStatus());
        profileEntity.setRole(profile.getRole());
        profileEntity.setPrtId(prt);
        profileRepository.save(profileEntity);
        toDto(profileEntity);

        return profile;
    }

    public boolean update(int id,ProfileDTO profile){
        Optional<ProfileEntity> optional= profileRepository.findById(id);
        if (optional.isEmpty()){
            throw new AppBadRequestException("Profile not found");
        }

        ProfileEntity profileEntity = optional.get();

        if(profile.getName()!=null){
            profileEntity.setName(profile.getName());
        }
        else if (profile.getSurname()!=null) {
            profileEntity.setSurname(profile.getSurname());
        }
        if (profile.getPhone()!=null) {
            profileEntity.setPhone(profile.getPhone());
        }
        if (profile.getEmail()!=null) {
            profileEntity.setEmail(profile.getEmail());
        }
        if (profile.getPassword()!=null) {
            profileEntity.setPassword(MD5Util.encode(profile.getPassword()));
        }

        profileRepository.save(profileEntity);
        return true;
    }

    public PageImpl<ProfileDTO> getByPagination(int page, int size){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<ProfileEntity> pageEntity = profileRepository.findAll(pageable);
        List<ProfileEntity> entities = pageEntity.getContent();
        List<ProfileDTO> dtos = new ArrayList<>();

        entities.forEach(e->dtos.add(toDto(e)));
        return new PageImpl<>(dtos,pageable,pageEntity.getTotalElements());
    }

    public boolean delete(int id){
        if (profileRepository.existsById(id)){
            profileRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean updatePhoto(int id,String photoId){
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Profile not found");
        }

        ProfileEntity profileEntity = optional.get();

        if (photoId!=null){
            profileEntity.setPhotoId(photoId);
        }
        if (profileEntity.getPhotoId()==null){
            profileEntity.setPhotoId(null);
        }
        profileRepository.save(profileEntity);
        return true;
    }

    public ProfileDTO toDto(ProfileEntity profileEntity){
        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setId(profileEntity.getId());
        profileDTO.setName(profileEntity.getName());
        profileDTO.setSurname(profileEntity.getSurname());
        profileDTO.setEmail(profileEntity.getEmail());
        profileDTO.setPhone(profileEntity.getPhone());
        profileDTO.setPassword(profileEntity.getPassword());
        profileDTO.setStatus(profileEntity.getStatus());
        profileDTO.setRole(profileEntity.getRole());
        profileDTO.setPhotoId(profileEntity.getPhotoId());

        return profileDTO;
    }

    void isValidProfile(ProfileDTO dto){
        if(dto.getName()==null || dto.getName().isBlank() || dto.getName().length()<3){
            throw new AppBadRequestException("Name required");
        }

        else if(dto.getSurname()==null || dto.getSurname().isBlank() || dto.getSurname().length()<3){
            throw new AppBadRequestException("Surname required");
        }

        else if (dto.getPhone()==null || dto.getPhone().isBlank() || dto.getPhone().length()<6){
            throw new AppBadRequestException("Phone required");
        }

        else if (dto.getEmail()==null || dto.getEmail().isBlank() || dto.getEmail().length()<5 || !dto.getEmail().endsWith("@gmail.com")) {
            throw new AppBadRequestException("Email required");
        }
        else if (dto.getPassword()==null || dto.getPassword().isBlank()) {
            throw new AppBadRequestException("Password required");
        }
    }

}

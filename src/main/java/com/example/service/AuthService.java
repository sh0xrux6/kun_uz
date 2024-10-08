package com.example.service;

import com.example.Enum.ProfileRole;
import com.example.dto.*;
import com.example.Entity.ProfileEntity;
import com.example.Enum.ProfileStatus;
import com.example.exp.AppBadRequestException;
import com.example.repository.ProfileRepository;
import com.example.util.JWTUtil;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MailSenderService mailSenderService;

    public ApiResponseDTO login(AuthDTO dto){
        Optional<ProfileEntity> optional = profileRepository.findByPhone(dto.getPhone());
        if(optional.isEmpty()){
            return new ApiResponseDTO(false,"Login or password not found.");
        }
        ProfileEntity entity = optional.get();
        if(!entity.getPassword().equals(MD5Util.encode(dto.getPassword()))){
            return new ApiResponseDTO(false,"password not found.");
        }
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE) || !entity.getVisible()){
            return new ApiResponseDTO(false,"Your status is not active,Please contact with support.");
        }

        ProfileDTO response = new  ProfileDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSurname(entity.getSurname());
        response.setPhone(entity.getPhone());
        response.setRole(entity.getRole());
        response.setJwt(JWTUtil.encode(entity.getId(), entity.getRole()));

        return new ApiResponseDTO(true,response);
    }

    public ApiResponseDTO register(RegistrationDTO dto){
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());

        if(optional.isPresent()){
            if(optional.get().getStatus().equals(ProfileStatus.REGISTRATION)){
                profileRepository.delete(optional.get());
            }
            return new ApiResponseDTO(false,"Email already in use.");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(entity.getRole());
        entity.setRole(ProfileRole.USER);
        profileRepository.save(entity);

        mailSenderService.sendEmailVerification(dto.getEmail(), dto.getName(), entity.getId());
        return new ApiResponseDTO(true, "The verification link was sent to email");
    }

    public ApiResponseDTO verification(String jwt) {
        JwtDTO jwtDTO = JWTUtil.decodeEmailJwt(jwt);

        Optional<ProfileEntity> exists = profileRepository.findById(jwtDTO.getId());
        if(exists.isEmpty()){
            return  new ApiResponseDTO(false, "Profile not found");
        }

        ProfileEntity entity = exists.get();
        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)){
            throw new AppBadRequestException("Wrong status");
        }

        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        return new  ApiResponseDTO(true, "Registration Complete");
    }
}

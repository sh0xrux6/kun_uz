package com.example.config;

import com.example.Entity.ProfileEntity;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<ProfileEntity> optional = profileRepository.findByPhone(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("Login or password is wrong");
        }

        ProfileEntity profile = optional.get();

        return  new CustomUserDetails(profile);
    }
}

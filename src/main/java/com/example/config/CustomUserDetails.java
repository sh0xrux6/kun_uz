package com.example.config;

import com.example.Entity.ProfileEntity;
import com.example.Enum.ProfileStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private ProfileEntity profileEntity;

    public CustomUserDetails(ProfileEntity profileEntity) {
        this.profileEntity = profileEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new LinkedList<>();
        list.add(new SimpleGrantedAuthority(profileEntity.getRole().name()));
        return list;
    }

    @Override
    public String getPassword() {
        return profileEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return profileEntity.getPhone();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return profileEntity.getStatus().equals(ProfileStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() { // payment infos
        return true;
    }

    @Override
    public boolean isEnabled() {
        return profileEntity.getVisible();
    }

}

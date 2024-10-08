package com.example.config;


import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;


@Component
@EnableWebSecurity
public class SpringSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;


    public static String[] AUTH_WHITELIST = {
            "auth/login",
            "auth/register",
            "auth/verification/email/{jwt}",
            "article/last/five",
            "article/last/three",
            "article/last/eight",
            "article/byIdAndLang/{id}",
            "article/last/four/except",
            "article/mostViewed",
            "article/last/fiveByTypeAndRegion",
            "article/listByRegionPagination",
            "article/last/fiveByCategory",
            "article/listByCategoryPagination",
            "article/increase/viewCount/{id}",
            "article/increase/shareCount/{id}",
            "article/getPagination/filter",
            "/article_like/like",
            "/article_like/dislike",
            "/article_like/remove",
            "/articleType/getByLanguage",
            "/attach/upload",
            "/attach/open/{fileName}",
            "/attach/open/general/{id}",
            "/attach//download",
            "/category/getByLanguage",
            "/comment/create",
            "/comment/update",
            "/comment/delete/{id}",
            "/comment/list/article_id",
            "/comment/list/repliedComment",
            "/commentLike/like",
            "/commentLike/dislike",
            "/commentLike/delete",
            "/mailHistory/get/history",
            "/mailHistory/get/history/ByDate",
            "/profile/create",
            "/profile/update/{id}",
            "/profile/update/image/{id}",
            "/profile/getByPagination",
            "region/getByLanguage",
            "/saved_article/create",
            "/saved_article/delete/{id}",
            "/saved_article/get/{id}"
    };

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    private PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return MD5Util.encode(rawPassword.toString()).equals(encodedPassword);
            }
        };
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((c)-> c.requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}

package com.example.gymapp.config;


import com.example.gymapp.service.CustomUserDetailFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final CustomUserDetailFacade customUserDetailService;

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailService::loadUserByUsername;
    }
}

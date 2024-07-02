package com.example.gymapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailFacade extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
}

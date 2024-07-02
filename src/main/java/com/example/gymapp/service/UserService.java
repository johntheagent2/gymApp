package com.example.gymapp.service;

import com.example.gymapp.dto.response.UserInfoResponse;
import com.example.gymapp.entity.User;

import java.util.Optional;

import java.util.List;

public interface UserService {

    Optional<User> findByUsername(String username);

    UserInfoResponse getCurrentUser();

    void save(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    User updateUser(User user);

    void deleteUser(Long id);
}


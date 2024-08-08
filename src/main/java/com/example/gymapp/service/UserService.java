package com.example.gymapp.service;

import com.example.gymapp.dto.request.UserTargetRequest;
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

    void updateUserTarget(UserTargetRequest user);

    boolean isUserHaveTarget();

    void deleteUser(Long id);
}


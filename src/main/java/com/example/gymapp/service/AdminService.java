package com.example.gymapp.service;

import com.example.gymapp.entity.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    Optional<Admin> findByUsername(String username);

    void save(Admin admin);

    Optional<Admin> findById(Long id);

    List<Admin> findAll();

    Admin updateUser(Admin user);

    void deleteUser(Long id);
}

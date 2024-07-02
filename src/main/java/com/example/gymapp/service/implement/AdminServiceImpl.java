package com.example.gymapp.service.implement;

import com.example.gymapp.entity.Admin;
import com.example.gymapp.repository.AdminRepository;
import com.example.gymapp.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Optional<Admin> findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public void save(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Admin> findAll() {
        return null;
    }

    @Override
    public Admin updateUser(Admin user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}

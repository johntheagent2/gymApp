package com.example.gymapp.service.implement;

import com.example.gymapp.common.Global;
import com.example.gymapp.dto.response.UserInfoResponse;
import com.example.gymapp.entity.User;
import com.example.gymapp.exception.exception.NotFoundException;
import com.example.gymapp.repository.UserRepository;
import com.example.gymapp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserInfoResponse getCurrentUser() {
        String username = Global.getCurrentLogin().getUsername();
        return findByUsername(username)
                .map(userAccount -> UserInfoResponse.builder()
                        .firstName(userAccount.getFirstName())
                        .lastName(userAccount.getLastName())
                        .profilePicture(userAccount.getProfilePicture())
                        .build())
                .orElseThrow(() -> new NotFoundException("user.username.username-not-found"));
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}

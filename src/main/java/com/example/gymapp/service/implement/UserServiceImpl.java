package com.example.gymapp.service.implement;

import com.example.gymapp.common.Global;
import com.example.gymapp.dto.request.UserTargetRequest;
import com.example.gymapp.dto.response.UserInfoResponse;
import com.example.gymapp.entity.User;
import com.example.gymapp.enumeration.ActivityFrequency;
import com.example.gymapp.exception.exception.NotFoundException;
import com.example.gymapp.repository.UserRepository;
import com.example.gymapp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
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
                        .gender(userAccount.getGender())
                        .birthYear((userAccount.getBrithYear()))
                        .weight(userAccount.getWeight())
                        .height(userAccount.getHeight())
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
    public void updateUserTarget(UserTargetRequest request) {
        String username = Global.getCurrentLogin().getUsername();
        User user = findByUsername(username)
                .orElseThrow(() -> new NotFoundException("authentication.user-name.not-found"));

        Double weight = request.getWeight();
        Double height = request.getHeight();
        ActivityFrequency activityLevel = request.getActivityFrequency();
        int age = calculateAge(user.getBrithYear());

        double bmr;
        if (user.getGender().equalsIgnoreCase("Male")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        double tdee;
        switch (activityLevel) {
            case SEDENTARY -> tdee = bmr * 1.2;
            case LIGHTLY -> tdee = bmr * 1.375;
            case MODERATELY -> tdee = bmr * 1.55;
            case VERY -> tdee = bmr * 1.725;
            case SUPER -> tdee = bmr * 1.9;
            default -> throw new IllegalStateException("Unexpected value: " + activityLevel);
        }

        double targetProtein = (tdee * 0.30) / 4;
        double targetFat = (tdee * 0.30) / 9;
        double targetCarbs = (tdee * 0.40) / 4;

        user.setWeight(weight);
        user.setHeight(height);
        user.setTargetCalories(round(tdee, 0));
        user.setTargetProtein(round(targetProtein, 0));
        user.setTargetFat(round(targetFat, 0));
        user.setTargetCarbs(round(targetCarbs, 0));

        save(user);
    }

    @Override
    public boolean isUserHaveTarget() {
        String username = Global.getCurrentLogin().getUsername();
        User user = findByUsername(username)
                .orElseThrow(() -> new NotFoundException("authentication.user-name.not-found"));

        return user.getTargetCalories() != null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    public int calculateAge(int birthYear) {
        return Year.now().getValue() - birthYear;
    }

    private double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}

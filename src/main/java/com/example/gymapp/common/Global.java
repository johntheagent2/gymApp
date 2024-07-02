package com.example.gymapp.common;

import com.example.gymapp.exception.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Random;
import java.util.UUID;

@AllArgsConstructor
public class Global {

    public static String otpGenerator(){
        StringBuilder otp = new StringBuilder();
        Random random = new Random();

        // Generate 6 random digits
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    public static String UUIDgenrator(){
        return UUID.randomUUID().toString();
    }

    public static UserDetails getCurrentLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return (UserDetails) authentication.getPrincipal();
        } else {
            throw new BadRequestException("security.core.userdetails");
        }
    }
}

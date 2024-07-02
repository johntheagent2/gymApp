package com.example.gymapp.service.implement;

import com.example.gymapp.entity.CustomUserDetails;
import com.example.gymapp.exception.exception.NotFoundException;
import com.example.gymapp.exception.exception.UnauthorizedException;
import com.example.gymapp.service.AdminService;
import com.example.gymapp.service.CustomUserDetailFacade;
import com.example.gymapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailServiceImpl implements CustomUserDetailFacade {

    private final UserService userService;
    private final AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetails userDetails = findUserDetailsByEmail(username);
        validateUserDetails(userDetails);
        return userDetails;
    }

    private CustomUserDetails findUserDetailsByEmail(String username) {
        return userService.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseGet(() -> adminService.findByUsername(username)
                        .map(CustomUserDetails::new)
                        .orElseThrow(() -> new NotFoundException(
                                "authentication.user-name.not-found"))
                );
    }

    private void validateUserDetails(CustomUserDetails userDetails) {
        if (userDetails.getAccount().getLoginAttempt() >= 3) {
                throw new UnauthorizedException("authentication.account.login-exceeded");
        }

    }
}

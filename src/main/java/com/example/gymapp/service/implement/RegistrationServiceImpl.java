package com.example.gymapp.service.implement;

import com.example.gymapp.dto.request.RegistrationRequest;
import com.example.gymapp.entity.User;
import com.example.gymapp.exception.exception.BadRequestException;
import com.example.gymapp.service.AccountService;
import com.example.gymapp.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final AccountService accountService;

    @Override
    public void register(RegistrationRequest request) {
        if(accountService.isUsernameExisted(request.getUsername())) {
            throw new BadRequestException("user.username.existed");
        }
        accountService.save(request, User.class);
    }
}

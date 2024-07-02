package com.example.gymapp.controller.common;

import com.example.gymapp.dto.request.RegistrationRequest;
import com.example.gymapp.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${common-mapping}/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping()
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequest request) {
        registrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

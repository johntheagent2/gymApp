package com.example.gymapp.service;

import com.example.gymapp.dto.request.AuthenticationRequest;
import com.example.gymapp.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}

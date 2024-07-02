package com.example.gymapp.controller.common;

import com.example.gymapp.dto.request.AuthenticationRequest;
import com.example.gymapp.dto.response.AuthenticationResponse;
import com.example.gymapp.enumeration.CommonConstant;
import com.example.gymapp.service.AuthenticationService;
import com.example.gymapp.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${common-mapping}/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final CookieService cookieService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request){
        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,
                cookieService.generateSessionCookie(CommonConstant.ACCESS_TOKEN_COOKIE_NAME, response.getJwtToken(), request.getServerName()));
        headers.add(HttpHeaders.SET_COOKIE,
                cookieService.generateSessionCookie(CommonConstant.REFRESH_TOKEN_COOKIE_NAME, response.getRefreshToken(), request.getServerName()));
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}

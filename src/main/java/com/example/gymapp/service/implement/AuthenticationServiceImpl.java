package com.example.gymapp.service.implement;

import com.example.gymapp.config.security.jwtConfig.JwtService;
import com.example.gymapp.dto.request.AuthenticationRequest;
import com.example.gymapp.dto.response.AuthenticationResponse;
import com.example.gymapp.entity.CustomUserDetails;
import com.example.gymapp.entity.base.Account;
import com.example.gymapp.exception.exception.BadCredentialException;
import com.example.gymapp.service.AccountService;
import com.example.gymapp.service.AuthenticationService;
import com.example.gymapp.service.CustomUserDetailFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailFacade customUserDetailService;
    private final AccountService accountService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        CustomUserDetails account;
        String jwtToken;
        String refreshToken;
        String jti;

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()));
            account = (CustomUserDetails) customUserDetailService.loadUserByUsername(request.getUsername());
        }catch (BadCredentialsException e){
            account = (CustomUserDetails) customUserDetailService.loadUserByUsername(request.getUsername());
            updateCountWrongLogin(account.getAccount());
            throw new BadCredentialException("authentication.password.incorrect");
        }


        jti = jwtService.generateJti();
        jwtToken = jwtService.generateToken(account, jti);
        refreshToken = jwtService.generateRefreshToken(account, jti);
//        sessionService.saveSession(refreshToken, account.getAccount());
        resetWrongLoginCounter(account.getAccount());

        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void updateCountWrongLogin(Account account){
        accountService.updateCountWrongLogin(account);
    }

    private void resetWrongLoginCounter(Account account){
        accountService.resetWrongLoginCounter(account);
    }
}

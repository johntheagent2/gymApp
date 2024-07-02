package com.example.gymapp.service.implement;

import com.example.gymapp.service.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CookieServiceImpl implements CookieService {
    @Override
    public String generateSessionCookie(String cookieName, String cookieValue, String serverName) {
        return ResponseCookie.from(cookieName, cookieValue)
                .domain(serverName)
                .path("/")
                .sameSite("LAX")
                .httpOnly(true)
                .secure(false)
                .build().toString();
    }

    @Override
    public ResponseCookie generateCookieForNewAccessToken(String cookieName, String cookieValue) {
        return ResponseCookie.from(cookieName, cookieValue)
                .domain("localhost")
                .path("/")
                .sameSite("LAX")
                .httpOnly(true)
                .secure(false)
                .build();
    }

    @Override
    public Optional<Cookie> getCookieByName(HttpServletRequest request, String cookieName) {
        return Optional.ofNullable(WebUtils.getCookie(request, cookieName));
    }
}

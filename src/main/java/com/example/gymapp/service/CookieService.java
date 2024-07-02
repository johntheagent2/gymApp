package com.example.gymapp.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

import java.util.Optional;

public interface CookieService {
    String generateSessionCookie(String cookieName, String cookieValue, String serverName);
    ResponseCookie generateCookieForNewAccessToken(String cookieName, String cookieValue);
    Optional<Cookie> getCookieByName(HttpServletRequest request, String cookieName);
}

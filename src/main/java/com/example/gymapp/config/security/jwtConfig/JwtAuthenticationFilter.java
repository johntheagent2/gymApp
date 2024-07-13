package com.example.gymapp.config.security.jwtConfig;

import com.example.gymapp.enumeration.CommonConstant;
import com.example.gymapp.exception.dto.ApiExceptionResponse;
import com.example.gymapp.exception.exception.ExpiredJwtException;
import com.example.gymapp.service.CookieService;
import com.example.gymapp.service.implement.CustomUserDetailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private CookieService cookieService;
    private CustomUserDetailServiceImpl customUserDetailService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String email;

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            jwtToken = jwtService.extractJwtToken(authHeader);
            email = jwtService.extractEmail(jwtToken);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailService.loadUserByUsername(email);

                if (jwtService.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }catch (JwtException e){
            ObjectMapper mapper = new ObjectMapper();
            ApiExceptionResponse api = new ApiExceptionResponse("JWT expired", e.getClass().getTypeName());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mapper.writeValueAsString(api));
        }
    }

    private String handleExpiredToken(UserDetails userDetails, String refreshToken, String serverName){
        if(!jwtService.isExpired(refreshToken)){
            String jti = jwtService.extractJti(refreshToken);
            String newToken = jwtService.generateToken(userDetails,jti);
            return cookieService.generateSessionCookie(CommonConstant.ACCESS_TOKEN_COOKIE_NAME, newToken, serverName);
        }else{
            throw new ExpiredJwtException("session.jwt.jti-is-expired");
        }
    }
}

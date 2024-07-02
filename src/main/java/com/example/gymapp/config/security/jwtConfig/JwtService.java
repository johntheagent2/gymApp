package com.example.gymapp.config.security.jwtConfig;

import com.example.gymapp.exception.exception.ExpiredJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtService {

    @Value("${secret-key}")
    private String SECRET_KEY;

//    @Value("#{1000 * 60 * 60 * 24}")
    @Value("#{15 * 1000}")
    private int ACCESS_TOKEN_EXPIRATION_TIME; // Access token expires in 1 day

    @Value("#{1000 * 60 * 60 * 24 * 7}")
    private int REFRESH_TOKEN_EXPIRATION_TIME; // Refresh token expires in 7 days

    public String extractJwtToken(String authHeader){
        return authHeader.substring(7);
    }

    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public String extractJti(String token){
        return extractClaim(token, Claims::getId);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaim(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaim(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigninKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (io.jsonwebtoken.ExpiredJwtException e){
            return e.getClaims();
        }
    }

    public String generateToken(UserDetails userDetails, String jti){
        return generateToken(new HashMap<>(), userDetails, jti, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(UserDetails userDetails, String jti){
        return generateToken(new HashMap<>(), userDetails, jti, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(UserDetails userDetails, String jti, Date issuedTime, Date expirationTime){
        return generateToken(new HashMap<>(), userDetails, jti, issuedTime, expirationTime);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, String jti, Date issuedTime, Date expirationTime){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setId(jti)
                .setIssuedAt(issuedTime)
                .setExpiration(expirationTime)
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, String jti, long expirationTime){

        Date current = new Date(System.currentTimeMillis());
        Date expiration = new Date(current.getTime() + expirationTime);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setId(jti)
                .setIssuedAt(current)
                .setExpiration(expiration)
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJti(){
        return UUID.randomUUID().toString();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String email = extractEmail(token);
        return email.equals(userDetails.getUsername());
    }

    public boolean isExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private boolean isTokenExpired(String token) {
        boolean isExpired = extractExpiration(token).before(new Date());
        if(isExpired){
            throw new ExpiredJwtException("session.jwt.jti-is-expired");
        }
        return false;
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public Date extractIssueAt(String token){
        return extractClaim(token, Claims::getIssuedAt);
    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

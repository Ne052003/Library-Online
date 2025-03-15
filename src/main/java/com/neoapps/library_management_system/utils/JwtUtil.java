package com.neoapps.library_management_system.utils;

import com.neoapps.library_management_system.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECURITY_KEY = "n8$!e@4yM%G7pLsC&QzXvF#B2*J^R5+8a3d";
    private static final long EXPIRATION_TIME = 3600000;

    private final Key key = Keys.hmacShaKeyFor(SECURITY_KEY.getBytes());

    public String generateToken(User user) {
        return Jwts.builder().setSubject(user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECURITY_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}

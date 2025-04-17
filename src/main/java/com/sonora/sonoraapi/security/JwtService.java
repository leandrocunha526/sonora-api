package com.sonora.sonoraapi.security;

import com.sonora.sonoraapi.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        return claims;
    }
    public String generateToken(User user) {
        Map<String, Object> extraClaims = generateExtraClaims(user);
        return Jwts.builder()
                .setSubject(user.getCpf())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extração do CPF (usuário) do token
    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // Validação do token
    public boolean isTokenValid(String token, User user) {
        return extractUsername(token).equals(user.getCpf()) && !isTokenExpired(token);
    }

    // Verifica se o token está expirado
    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
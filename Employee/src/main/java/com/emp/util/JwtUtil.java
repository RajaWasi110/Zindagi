package com.emp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Create a token with claims and subject (username)
    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + 900000)) //expiration time
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Generate a token
    public String generateToken(String username) {
        long expirationTime = 900000; // 15 min
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("MyApp")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Validate the token
    public boolean validateToken(String token) throws JwtException {
        Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        return false;
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract a specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        boolean expired = expiration == null || expiration.before(new Date());
        System.out.println("Token expiration check - Expired: " + expired + " | Expiration Time: " + expiration);
        return expired;
    }

    // Extract expiration date
    private Date extractExpiration(String token) {
        Claims claims = extractAllClaims(token);
        Date expiration = claims.getExpiration();
        System.out.println("Token expiration: " + expiration); // Add logging
        return expiration;
    }
}

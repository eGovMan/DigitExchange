package org.digit.exchange.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import org.digit.exchange.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value("${JWT_SECRET_KEY}")
    private static String SECRET_KEY;

    public JwtUtil(@Value("${JWT_SECRET_KEY}") String secretKey){
        JwtUtil.SECRET_KEY = secretKey;
    }

    public static SecretKey generateSecretKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public static String generateToken(String username) {
        long currentTimeMillis = System.currentTimeMillis();
        // Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        SecretKey key = JwtUtil.generateSecretKey(SECRET_KEY);

        return Jwts.builder()
                .subject(username)
                .expiration(new Date(currentTimeMillis + 3600000)) //expire one hour from now
                .notBefore(new Date(currentTimeMillis)) 
                .issuedAt(new Date()) 
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        SecretKey key = JwtUtil.generateSecretKey(SECRET_KEY);
        try{
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        // // SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");

        // // JwtParser jwtParser = Jwts.parser()
        // //     .verifyWith(secretKeySpec)
        // //     .build();
        // try {
        //     jwtParser.parse(token);
        // 
        } catch (JwtException e) {
            throw new CustomException("Could not verify JWT token integrity!", e);
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        SecretKey key = JwtUtil.generateSecretKey(SECRET_KEY);
        return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();                    
    }

}

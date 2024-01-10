package org.digit.exchange.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.model.Individual;
import org.digit.exchange.repository.IndividualRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value("${JWT_SECRET_KEY}")
    private static String SECRET_KEY;
    private static IndividualRepository individualRepository;

    public JwtUtil(@Value("${JWT_SECRET_KEY}") String secretKey, IndividualRepository individualRepository){
        JwtUtil.SECRET_KEY = secretKey;
        JwtUtil.individualRepository = individualRepository;
    }

    public static SecretKey generateSecretKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public static String generateToken(String username) {
        long currentTimeMillis = System.currentTimeMillis();
        // Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        SecretKey key = JwtUtil.generateSecretKey(SECRET_KEY);

        

        //Get the individual details
        Optional<Individual> individual = JwtUtil.individualRepository.findById(username);

        List<String> rolesWithPrefix = individual.get().getRoles().stream()
                                             .map(role -> "ROLE_" + role)
                                             .collect(Collectors.toList());
    
        if(individual.isPresent()){
            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", rolesWithPrefix);

            

            return Jwts.builder()
                    .subject(username)
                    .claims(claims)
                    .expiration(new Date(currentTimeMillis + 3600000)) //expire one hour from now
                    .notBefore(new Date(currentTimeMillis)) 
                    .issuedAt(new Date()) 
                    .signWith(key)
                    .compact();
        }
        return null;        
    }

    public boolean validateToken(String token) {
        SecretKey key = JwtUtil.generateSecretKey(SECRET_KEY);
        try{
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
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

    public List<SimpleGrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        List<?> rolesRaw = claims.get("roles", List.class);
        if (rolesRaw == null) {
            return Collections.emptyList();
        }
        List<String> roles = new ArrayList<>();
        for (Object roleObj : rolesRaw) {
            if (roleObj instanceof String) {
                roles.add((String) roleObj);
            }
        }
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
    }

}

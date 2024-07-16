
package com.cookswp.milkstore.utils;

import com.cookswp.milkstore.pojo.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${milkstore.app.jwtSecret}")
    private String jwtSecret;

    @Value("${milkstore.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            User userPrincipal = (User) authentication.getPrincipal();
            List<String> roles = userPrincipal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
            return buildToken(userPrincipal.getEmailAddress(), roles);
        } else if (authentication instanceof DefaultOAuth2User) {
            DefaultOAuth2User userPrincipal = (DefaultOAuth2User) authentication.getPrincipal();
            String email = Objects.requireNonNull(userPrincipal.getAttribute("email")).toString();
            String role = userPrincipal.getAttribute("role") != null ? userPrincipal.getAttribute("role").toString() : ""; // Optional: handle missing role attribute
            return buildToken(email, Collections.singletonList(role)); // Assuming a single role from OAuth2 provider
        } else {
            // Handle unexpected authentication type (optional)
            throw new IllegalArgumentException("Unsupported authentication type: " + authentication.getClass().getName());
        }
    }

    private String buildToken(String email, List<String> roles){
        Date date = new Date();
        Date issuedAtTime = new Date(date.getTime());
        Date expTime = new Date(date.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .subject(email)
                .issuedAt(issuedAtTime)
                .expiration(expTime)
                .claim("scope", roles)
                .signWith(getSigningKey())
                .compact();
    }

    public String buildTempToken(String email) {
        // Set expiration time to 5 minutes from now (in milliseconds)
        long fiveMinutesInMs = 5 * 60 * 1000;  // 5 minutes * 60 seconds/minute * 1000 milliseconds/second

        Date date = new Date();
        Date issuedAtTime = new Date(date.getTime());
        Date expTime = new Date(date.getTime() + fiveMinutesInMs);

        // Build the JWT with email as subject, expiration time, and omit roles claim
        return Jwts.builder()
                .subject(email)
                .issuedAt(issuedAtTime)
                .expiration(expTime)
                .signWith(getSigningKey())
                .compact();
    }

    public SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}

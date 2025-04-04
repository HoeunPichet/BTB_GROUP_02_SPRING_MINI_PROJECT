package com.example.project.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtUtils {
    private final String SECRET = "aGVsbG9ldmVyeW9uZXRoaXNpc215c2VjcmV0";
    private final long EXPIRATION = TimeUnit.MINUTES.toMillis(120);


    // generate token for user
    public String generateToken(String identifier) {
        return Jwts.builder()
                .setSubject(identifier)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(generateSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // generate secret key for signing token
    private SecretKey generateSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // retrieving any information from token (Need the secret key)
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // extract a specific claim from the JWT token’s claims (user's info)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // retrieve email from jwt token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // retrieve expiration date from jwt token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    // check expired token
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    // check if token is valid
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }

        return false;
    }
}

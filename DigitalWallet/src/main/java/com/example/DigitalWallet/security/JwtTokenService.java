package com.example.DigitalWallet.security;

import com.example.DigitalWallet.UserAuthority.Authority;
import com.example.DigitalWallet.model.Authorities;
import com.example.DigitalWallet.model.User;
import com.example.DigitalWallet.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenService {
    private final static String secretkey ="mysecret-hmac-sha256-mysecret-hmac-sha256-mysecret-hmac-sha256";

    private final UserRepository userRepository;

    public JwtTokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String username){
        Date now = new Date();
        Date expired = new Date(System.currentTimeMillis()+84600000);
        User user = userRepository.findByEmail(username);
        Set<Authorities> authorities = Authority.getAuthority(user.getRole());

        Map<String,Object> claim = new HashMap<>();
        claim.put("authority",authorities);
        claim.put("role",user.getRole());

        return Jwts.builder()
                .signWith(getSecretKey())
                .subject(username)
                .claims(claim)
                .issuedAt(now)
                .expiration(expired)
                .compact();
    }

    public Set getAuthority(String token){
        return extractToken(token).get("authority", Set.class);
    }

    public String getRole(String token){
        return  extractToken(token).get("role",String.class);
    }

    public String extractusername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean isExpired(String token){
        return !extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, String username){
        return isExpired(token) && extractusername(token).equalsIgnoreCase(username);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractToken(token);
        return claimsTFunction.apply(claims);
    }

    public Claims extractToken(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

package com.example.demo.security.jwt;

import com.example.demo.repository.TokenBlackListRepository;
import com.example.demo.requests.LoginRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTTokenProvider {

    @Autowired
    TokenBlackListRepository tokenBlackListRepository;

    private final long expiration = 3600000;
    private final String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String GenerateJWT(Authentication auth)
    {
        String username = auth.getName();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(username)                      // User identifier (usually the username)
                .setIssuedAt(new Date())                   // Token issue time
                .setExpiration(expiryDate)                 // Token expiration time
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)  // Sign with the secret key
                .compact();
    }


    // Extract username from JWT token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Extract expiration date from JWT token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Extract a single claim from the token
    // Here, first we extract all claims from token
    // then we extract require claim based on ClaimsResolver - getSubject or Get Expiration
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);

        System.out.println(claims);
        return claimsResolver.apply(claims);
    }


    // Extract all claims from the token
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }


    // Validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean isTokenBlackListed(String token)
    {
System.out.println(tokenBlackListRepository.existsByToken(token));
        return tokenBlackListRepository.existsByToken(token);
    }






    private Key getSignInKey() {
        // Decodes the Base64 encoded secret key (jwtSecret)
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        // Converts the decoded bytes into an HMAC SHA key for the HS512 algorithm
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

package com.barney.unionfly.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;

@Component
public class JwtService implements Serializable {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String createJwt(String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 18000000);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parseJwt(String token) {
        String tokenWithoutBear = token.replace("Bearer ", "");
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(tokenWithoutBear);
    }
}

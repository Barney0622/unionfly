package com.barney.unionfly.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;

@Component
public class JwtService implements Serializable {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    @Value("${server.jwt.expiration}")
    private Long expiration;
    @Value("${server.jwt.prefix}")
    private String prefix;

    public String createJwt(String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration * 1000);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parseJwt(String token) {
        String tokenWithoutBear = token.replace(prefix, "");
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(tokenWithoutBear);
    }
}

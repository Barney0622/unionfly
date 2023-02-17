package com.barney.unionfly.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.Key;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static String createJwt(String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 18000000);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public static Jws<Claims> parseJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

}

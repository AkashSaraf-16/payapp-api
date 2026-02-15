package com.payapp.api_gateway.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {
    private static final String SECRET = "nJzmNnJmPeAqO18LvRpEp9QZVIzupi49SYVyr1y7";

    private static Key getSigniningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public static Claims validateToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigniningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

package com.tbm.task_budget_manager.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    // 🔑 السر لازم يكون طويل (على الأقل 32 character للـ HS256)
    private static final String SECRET = "my-super-secret-key-for-jwt-which-should-be-long-and-secure";
    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24h

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // توليد JWT مع username + role
    public String generateUserToken(String username) {
        return Jwts.builder()
                .subject(username)
                .claims(Map.of("role", "USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    // استخراج username من التوكن
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // التحقق من صلاحية التوكن
    public boolean isValid(String token, String username) {
        try {
            var claims = Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String extracted = claims.getSubject();
            Date exp = claims.getExpiration();
            return extracted.equals(username) && exp.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}

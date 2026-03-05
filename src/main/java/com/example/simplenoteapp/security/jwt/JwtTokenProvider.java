package com.example.simplenoteapp.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final long validityInMilliseconds;
    private final Key key;

    // 생성자를 통해 설정값 주입 및 Key 객체 초기화
    // 이렇게 하면 @PostConstruct 없이도 객체 생성 즉시 Key가 만들어져 테스트 에러가 방지됩니다.
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration_time}") long validityInMilliseconds) {
        this.secretKey = secretKey;
        this.validityInMilliseconds = validityInMilliseconds;

        // Base64로 인코딩된 키를 디코딩하여 HMAC-SHA 키 생성
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 1. 토큰 생성 메서드
    public String generateToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role); // 권한 정보 추가

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256) // 암호화 알고리즘 지정
                .compact();
    }

    // 2. 토큰에서 사용자 아이디(Subject) 추출
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 3. 유효성 검사 (예외 발생 시 JwtException을 던져 필터에서 처리 유도)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // 만료된 경우 구체적인 메시지 전달
            throw new JwtException("만료된 토큰입니다.");
        } catch (JwtException | IllegalArgumentException e) {
            // 위조되거나 잘못된 형식인 경우
            throw new JwtException("유효하지 않은 토큰입니다.");
        }
    }
}
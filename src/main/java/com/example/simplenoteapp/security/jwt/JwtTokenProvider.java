package com.example.simplenoteapp.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


//모든 요청을 감시하는 필터
@Component
public class JwtTokenProvider {
    //1.설정 파일의 secret key 가져오기
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration_time}")
    private long validityInMilliseconds;

    private Key key;

    //객체 생성 후 secretKey를 암호화 키로 변환
    @PostConstruct
    protected void init(){
        byte[] ketBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(ketBytes);
    }
    //2.토큰 생성 메서드
    public String generateToken(String username, String role){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role",role); //권한 정보 추가

       Date now = new Date();
       Date validity = new Date(now.getTime() + validityInMilliseconds);
       return Jwts.builder()
               .setClaims(claims)
               .setIssuedAt(now)
               .setExpiration(validity)
               .signWith(key, SignatureAlgorithm.HS256) //HMAC + SHA256암호화
               .compact();
    }

    //3. 토큰에서 사용자 아이디(Subject) 추출
    public String getUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key) //우리가 만든 키로 복호화
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); //생성할 때 넣은 username이 나온다.
    }
    //4.유효성 검사 -> GloExHandler로 던지기
    public boolean vaildateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(ExpiredJwtException e){
            //false 리턴 대신 예외를 던짐
            throw new JwtException("만료된 토큰입니다");
        }catch(JwtException | IllegalArgumentException e){
            throw new JwtException("유효하지 않은 토큰 입니다");
        }
    }
}
/**
 * 2. 필터(JwtAuthenticationFilter)에서의 처리
 * 여기서 아주 중요한 포인트가 있습니다. 필터(Filter)는 서블릿(Servlet)보다 앞단에 있기 때문에, 일반적인 @RestControllerAdvice가 바로 잡지 못할 수도 있습니다.
 *
 * 그래서 보통 두 가지 방법을 씁니다:
 *
 * 방법 A: 필터 안에서 예외가 발생하면 바로 에러 응답(JSON)을 직접 그려준다.
 *
 * 방법 B: 예외를 HttpServletRequest에 담아 컨트롤러로 넘긴 뒤 처리한다.
 */
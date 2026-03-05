package com.example.simplenoteapp.security.jwt;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JwtTokenProviderTest {
    //1.테스트에 사용할 가짜(Mock) 데이터 준비
    //  - 출분히 긴 비밀키 (최소 32자 이상 추천)
    //  - 토큰 유효 시간( 예: 1시간)
    //2. 테스트 대상(Provider)선언

    private JwtTokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        // 1. 아주 긴 테스트용 비밀키 (직접 문자열로 입력)
        String testSecretKey = "bXktc2VjcmV0LWtleS1mb3ItdGVzdGluZy1wdXJwb3Nlcy1vbmx5LTMyLWNoYXJz";

        // 2. 유효 시간 설정
        long validity = 3600000L;

        // 3. 생성자에 이 값들을 직접 전달하여 객체 생성
        // 만약 JwtTokenProvider 생성자가 이 값을 받도록 설계되어 있는지 확인하세요.
        tokenProvider = new JwtTokenProvider(testSecretKey, validity);
    }
    @Test
    @DisplayName("액세스 토큰을 생성할 수 있다")
    void createAccessToken_success(){
        //[Given] 테스트용 사용자 아이디와 권한(ROLE_USER) 을 준비한다.
        String username = "testUser";
        String role = "ROLE_USER";
        //[When] Provider의 생성 메서드를 호출하여 토큰(String)을 만든다.
        String token = tokenProvider.generateToken(username, role);
        //[Then] 다음을 검증한다.
        //  -생성된 토큰이 비어있지(null) 않아야 한다.
        assertThat(token).isNotEmpty();
        //  -생성된 토큰을 다시 Provider에 넣어서 아이디를 추출했을 때,
        // [Given]에서 준비한 아이디와 똑같아야 한다.
        String extractedUsername = tokenProvider.getUsername(token);
        assertThat(extractedUsername).isEqualTo(username);



    }
    @Test
    @DisplayName("토큰에서 username을 추출할 수 있다")
    void validTokenSuccess(){
        //[Given] 정상적으로 생성된 토큰을 준비한다,
        String token = tokenProvider.generateToken("testUser","ROLE_USER");
        //[When] Provider의 유효성 검사 메서드(ValidationToken)를 호출한다,
        boolean isValid = tokenProvider.validateToken(token);
        //[Then] 결과값이 true여야 한다.
        assertThat(isValid).isTrue(); // "true문자열 비교가 아니라 실제 불리언 값을 체크해야 한다"
    }
    @Test
    @DisplayName("토큰에서 username을 추출하지 못한다")
    void invalidTokenSuccess(){
        //[Given]아무 글자나 적은 잘못된 토큰 문자열을 준비한다,
        String invalidToken ="this.is.invalid.token.stirng.skd";
        //[when] provider의 유효성 검사 메서드를 호출한다.
        boolean isValid = tokenProvider.validateToken(invalidToken);
        //[Then] 결과값이 false여야 한다,
        assertThat(isValid).isFalse();
    }

}
/**
 * tokenProvider.getUsername(token) :
 * 아까 작성한 코드에는 인자값을 username을 넣었는데, 실제로는 생성된 token을 넣어야 그 안의 암호를 풀어서 아이디를 가져온다.
 *
 * assertThat().isEqualTo(username)
 * == 연산자는 객체의 주소를 비교할 위험이 있지만, isEqualTo는 문자열의 내용이 같은지 정확이 체크해 준다.
 *
 * assertThat(token).isNotEmpty() :
 * 단순히 isNotNull()보다 강력하다 빈 문자열("")조차 허용하지 않겠다는 뜻
 */
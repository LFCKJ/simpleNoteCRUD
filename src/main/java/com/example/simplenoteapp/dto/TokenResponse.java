package com.example.simplenoteapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//토큰 응답용 DTO(accessToken)
//
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    //필드 1: 문자열 타입의 액세스_토근(JWT 문자열이 들어감)
    //필드 2: 문자열 타입의 토큰_타입(항상 "Bearer"라고 넣어줄 것)

    private String accessToken;
    @Builder.Default // 빌더를 써도 "Bearer"가 기본으로 들어가게 한다.
    private String tokenType ="Bearer";

}

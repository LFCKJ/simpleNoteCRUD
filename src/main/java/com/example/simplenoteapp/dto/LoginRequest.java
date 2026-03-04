package com.example.simplenoteapp.dto;
//로그인용DTO(아이디, 비번)

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 포함되어야 할 내용
 * 사용자 아이디: 누구인지 확인하기 위함
 * 비밀번호: 본인이 맞는지 증명하기 위함
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    //필드 1: 문자열 타입의 사용자_아이디(검증: 필수값)
    //필드 2: 문자열 타입의 비밀번호(검증: 필수값)

    @NotBlank(message="필수 입력값 입니다.")
    private String username;
    @NotBlank(message="필수 입력 값 입니다.")
    private String password;
    //닉네임 같은 정보는 로그인할 때 필요 없으므로 넣지 않는다.
}

package com.example.simplenoteapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자 아이디 * 중복되면 안됨 -> 비어있으면 안 됨
 * 비밀번호 * 비어있으면 안 됨 -> 보안을 위해 최소 같이 제한이 필요함
 * 닉네임 * 서비스에 표실될 이름 -> 비어있으면 안됨
 */
@Getter
@Setter //나중에 Build로 바꾸기
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {
    //필드 1: 문자열 타입의 사용자_아이디(검증: 필수값)
    //필드 2: 문자열 타입의 비밀번호 (검증: 필수값, 최소 4자 이상)
    //필드 3: 문자열 타입의 닉네임(검증: 필수값)
    @NotBlank(message ="필수 값 입니다")
    private String username;
    @NotBlank(message="값을 입력해주세요")
    @Size(min = 4, message="최소 4자 이상입니다.")
    private String password;
    @NotBlank(message ="필수 값 입니다")
    private String nickname;


    //참고: 엔티티로 변환할 때 'ROLE'은 보통 기본값(USER)으로 서비스에서 넣어준다.
}

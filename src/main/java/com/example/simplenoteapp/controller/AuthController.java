package com.example.simplenoteapp.controller;

import com.example.simplenoteapp.dto.JoinRequest;
import com.example.simplenoteapp.dto.LoginRequest;
import com.example.simplenoteapp.dto.TokenResponse;
import com.example.simplenoteapp.exception.ApiResponse;
import com.example.simplenoteapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//로그인, 회원가입 API 전달
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    //회원가입 api
    @PostMapping("/join")
    public ApiResponse<String> join(@Valid @RequestBody JoinRequest request){
        authService.join(request);
        return ApiResponse.success("회원가입에 성공했습니다");
    }

    //로그인 API
    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest request){
        TokenResponse response = authService.login(request);
        System.out.println("발급된 토큰" + response.getAccessToken());
        return ApiResponse.success(response);
    }
}

/**
 * @Vali 추가 : 우리가 DTO에 적어둔 @NotBlank나 @Size가 실제로 작동하려면 파라미터 앞에 붙어 있어야 한다.
 * 유지 보수 : 나중에 회원가입 항목이 늘어나도, authService.join(request) 코드는 한 줄도 수정할 필요가 없어진다.
 * 일관성 : 로그인이 성공했을 때 클라이언트는 accessToken과 tokenType이 담긴 예쁜 JSON 객체를 받게 된다.
 */
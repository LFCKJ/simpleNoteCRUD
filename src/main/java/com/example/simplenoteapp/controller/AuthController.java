package com.example.simplenoteapp.controller;

import com.example.simplenoteapp.dto.JoinRequest;
import com.example.simplenoteapp.dto.LoginRequest;
import com.example.simplenoteapp.exception.ApiResponse;
import com.example.simplenoteapp.service.AuthService;
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
    public ApiResponse<String> join(@RequestBody JoinRequest request){
        authService.join(request.getUsername(), request.getPassword(),request.getNickname());
        return ApiResponse.success("회원가입에 성공했습니다");
    }

    //로그인 API
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequest request){
        String token = authService.login(request.getUSername(), request.getPassword());
        return ApiResponse.success(token);
    }
}

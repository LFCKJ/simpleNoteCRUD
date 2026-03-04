package com.example.simplenoteapp.service;


import com.example.simplenoteapp.dto.JoinRequest;
import com.example.simplenoteapp.dto.LoginRequest;
import com.example.simplenoteapp.dto.TokenResponse;
import com.example.simplenoteapp.entity.Member;
import com.example.simplenoteapp.repository.MemberRepository;
import com.example.simplenoteapp.security.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //1.회원가입
    @Transactional
    public void join(JoinRequest joinRequest){
        //아이디 중복 체크
        if(memberRepository.existsByUsername(joinRequest.getUsername())){
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        //비밀번호 암호화 후 저장
        Member member = Member.builder()
                .username(joinRequest.getUsername())
                .password(passwordEncoder.encode(joinRequest.getPassword())) //BCrypt 암호화 필수
                .nickname(joinRequest.getNickname())
                .role(Member.Role.ROLE_USER)
                .build();
        memberRepository.save(member);
    }
    //2.로그인
    @Transactional
    public TokenResponse login(LoginRequest loginRequest){
        //아이디로 사용자 조회
        Member member = memberRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(()->new RuntimeException("가입되지 않은 아이디입니다"));

        //비밀번호 일치 여부 확인
        if(!passwordEncoder.matches(loginRequest.getPassword(),member.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        //로그인 성공 시 토큰 생성 및 반환
        String token =  jwtTokenProvider.generateToken(member.getUsername(), member.getRole().name());

        //4.응답 객체 생성 및 반환
        //리턴 타입 변경(String -> TokenResponse)
        //return 문에서 TokenResponse.builder()사용
        //.accessToken(token)을 넣고 .build() 하면 끝
        return TokenResponse.builder()
                .accessToken(token)
                .build();
    }

}

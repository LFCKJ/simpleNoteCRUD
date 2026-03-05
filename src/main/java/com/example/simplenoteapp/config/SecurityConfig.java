package com.example.simplenoteapp.config;

import com.example.simplenoteapp.security.jwt.JwtAuthenticationFilter;
import com.example.simplenoteapp.security.jwt.JwtTokenProvider;
import com.example.simplenoteapp.security.user.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//시큐리티 전체 설정(필터 체인, 암호화 빈 등)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService customUserDetailService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                //1.REST API 이므로 crsf 보안과 기본 로그인 폼은 끈다.
                .csrf(csrf->csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                //2.JWT를 쓰므로 세션을 서버에 생성하지 않는다.(Stateless)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //3. 요청에 대한 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() //로그인, 회원가입은 모두 허용
                        .requestMatchers("/api/notes/**").authenticated() //메모 관련은 로그인 필수
                        .anyRequest().permitAll())
                //4.우리가 만든 JWT필터를 시큐리티 필터 체인에 끼워 넣는다.
                //usernamePasswordAuthenticationFilter(기본 로그인 필터)가 실행되기 전에 우리 필터를 먼저 돌린다.
                .addFilterBefore( new JwtAuthenticationFilter(jwtTokenProvider),
                                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    //비밀번호 암호화 도구 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}

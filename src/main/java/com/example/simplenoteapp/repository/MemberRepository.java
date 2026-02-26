package com.example.simplenoteapp.repository;

import com.example.simplenoteapp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
//로그인을 위해 아이디로 회원 정보를 찾는 핵심 메서드
//Optional을 써서 사용자가 없을 때의 예외처리를 쉽게 만든다.
    Optional<Member> findByUserName(String username);
    //회원가입 시 중복 아이디 체크를 위해 사용
    boolean existsByUserName(String username);
}

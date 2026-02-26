package com.example.simplenoteapp.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.management.relation.Role;

//로그인에 필요한 아이디, 비밀번호, 권한(ROLE)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //무분별한 객체 생성 방지
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; //로그인할 때 사용할 아이디

    @Column(nullable = false)
    private String password; //암호화되어 저장될 비밀번호

    @Column(nullable = false)
    private String nickname; //서비스에서 표시될 이름

    @Enumerated(EnumType.STRING) //ENUM 번호가 아닌 문자열(ROLE_USER 등)로 DB 저장
    private Role role; //사용자의 권한( 일반 유저, 관리자 등)

    //권한을 정의하는 내부 ENUM(보통 따로 파일을 빼기도 한다)
    public enum Role{
        ROLE_USER, ROLE_ADMIN
    }


}
/**
 * @Column(unique = true) : 아이디는 중복될 수 없으므로 DB레벨에서 제약조근을 걸어 보안과 무결성을 지킨다.
 * ROLE 설정: JWT토큰을 만들 때 이 사용자가 일반 유저인지 관리자인지 정보를 넣어야 한다. 그래서 권한 필드가 필수이다.
 * NoArgsConstructor(access= AccessLevel.PROTECTED) : JPA는 기본 생성자가 필요하지만, 코드상에서 new MEMBER()
 * 로 아무 데이터 없는 객체를 만드는 것을 막아 안전성을 높인다.
 */
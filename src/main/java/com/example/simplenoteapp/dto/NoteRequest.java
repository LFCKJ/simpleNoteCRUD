package com.example.simplenoteapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자(Jackson 라이브러리가 JSON을 객체로 바꿀 때 필요)
@AllArgsConstructor //모든 필드를 포함한 생성자
public class NoteRequest {
    //Valid
    @NotBlank(message = "제목은 필수 입력 사항입니다")
    //NotBlank: null, "", " "(공백)을 모두 허용하지 않는다.
    @Size(min = 1, max = 20, message = "제목은 1자 이상 20자 이하로 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    //size를 지정하지 않으면 아주 긴 텍스트도 가능하다
    @Size(message="내용은 1000자 아래로 작성해주세요")
    private String content;

}

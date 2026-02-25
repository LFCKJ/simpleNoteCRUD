package com.example.simplenoteapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T>{
    private boolean success; //성공 여부
    private String message; // 안내 메시지
    private T data; //실제 데이터(성공 시에만 들어감)

    //성공 시 사용할 정적 메서드
    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(true, "요청이 성공적으로 처리되었습니다.", data);
    }
    //실패 시 사용할 정적 메서드
    public static <T> ApiResponse<T> fail(String message){
        return new ApiResponse<>(false, message, null);
    }
}

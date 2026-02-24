package com.example.simplenoteapp.exception;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp; //에러 발생 시간
    private final int status; //HTTP 상태 코드(예: 400)
    private final String error; //에러 유형(예: "BadRequest")
    private final String message; //우리가 설정한 에러 메시지

}

package com.example.simplenoteapp.exception;

import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionhandler {
    //1.서비스에서 던진 IllegalArgumentExceotion처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e){
          ErrorResponse response = ErrorResponse.builder()
                  .timestamp(LocalDateTime.now())
                  .status(HttpStatus.BAD_REQUEST.value())
                  .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                  .message(e.getMessage())
                  .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    //2,그 외 모든 예외 처리 (서버 내부 에러)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("서버에 예상치 못한 오류가 발생했습니다")
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //3.유효성 검사(@Valid)실패 시 발생하는 에러 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        // 유효성 검사에 실패한 에러들 중 첫 번째 에러의 메시지를 가져옴
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(message)
                .build();//DTO에 적은 Message가 출력
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}

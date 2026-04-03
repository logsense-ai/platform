package com.logsense.platform.common.exception;

import com.logsense.platform.common.BasicResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BasicResponseDto<Void>> handleCustomException(CustomException e) {

        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(BasicResponseDto.of(
                        errorCode.getHttpStatus().value(),
                        errorCode.getMessage(),
                        null
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BasicResponseDto<Void>> handleValidationException(MethodArgumentNotValidException e) {

        String message = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : "잘못된 요청입니다.";

        return ResponseEntity
                .badRequest()
                .body(BasicResponseDto.of(HttpStatus.BAD_REQUEST.value(), message, null));
    }
}
package com.logsense.platform.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(staticName = "of")
public class BasicResponseDto<T> {
    private final int statusCode;
    private final String message;
    private final T data;

    public static <T> BasicResponseDto<T> success(String message, T data) {
        return of(HttpStatus.OK.value(), message, data);
    }

    public static <T> BasicResponseDto<T> fail(String message) {
        return of(HttpStatus.BAD_REQUEST.value(), message, null);
    }

}

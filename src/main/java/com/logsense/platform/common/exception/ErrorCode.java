package com.logsense.platform.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_LOG_INGEST_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 로그 요청입니다."),
    EMPTY_RAW_LOG_LINE(HttpStatus.BAD_REQUEST, "rawLogLine은 비어 있을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
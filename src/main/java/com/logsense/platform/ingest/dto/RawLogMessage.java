package com.logsense.platform.ingest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "파싱된 원시 로그 메시지")
public record RawLogMessage(

        @Schema(description = "로그 발생 시각", example = "2026-03-26T10:15:30")
        LocalDateTime timestamp,

        @Schema(description = "로그 레벨", example = "ERROR")
        String level,

        @Schema(description = "로거명 또는 서비스명", example = "user-service")
        String logger,

        @Schema(description = "스레드명", example = "main")
        String thread,

        @Schema(description = "로그 메시지", example = "NullPointerException")
        String message,

        @Schema(description = "추적용 traceId", example = "trace-1234")
        String traceId,

        @Schema(description = "원본 로그 한 줄", example = "2026-03-26 10:15:30 ERROR user-service [main] NullPointerException")
        String rawLine
) {
}
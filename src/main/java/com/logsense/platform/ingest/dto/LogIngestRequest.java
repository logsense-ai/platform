package com.logsense.platform.ingest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LogIngestRequest(

        @Schema(
                description = "원시 로그 한 줄",
                example = "2026-03-26 10:15:30 ERROR user-service [main] NullPointerException"
        )
        @NotBlank(message = "rawLogLine은 비어 있을 수 없습니다.")
        String rawLogLine
) {
}
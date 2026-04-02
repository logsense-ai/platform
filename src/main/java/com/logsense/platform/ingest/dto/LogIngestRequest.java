package com.logsense.platform.ingest.dto;

import jakarta.validation.constraints.NotBlank;

public record LogIngestRequest(

        @NotBlank(message = "rawLogLine은 비어 있을 수 없습니다.")
        String rawLogLine
) {
}

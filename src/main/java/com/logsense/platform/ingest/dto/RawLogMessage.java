package com.logsense.platform.ingest.dto;

import java.time.LocalDateTime;

public record RawLogMessage(
        LocalDateTime timestamp,
        String level,
        String logger,
        String thread,
        String message,
        String traceId,
        String rawLine
) {
}

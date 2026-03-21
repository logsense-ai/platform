package com.logsense.platform.common.logging.event;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AppLogEvent {
    private final String traceId;
    private final String logLevel;
    private final String loggerName;
    private final String message;
    private final String threadName;
    private final LocalDateTime createdAt;
}

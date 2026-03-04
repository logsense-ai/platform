package com.logsense.platform.common.logging.service;

import com.logsense.platform.common.logging.event.LogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LogEventService {

    private final ApplicationEventPublisher publisher;

    public void log(String logLevel, String loggerName, String message, String traceId) {
        publisher.publishEvent(
                LogEvent.builder()
                        .traceId(traceId)
                        .logLevel(logLevel)
                        .loggerName(loggerName)
                        .message(message)
                        .threadName(Thread.currentThread().getName())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }
}
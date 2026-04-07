package com.logsense.platform.ingest.parser;

import com.logsense.platform.ingest.dto.RawLogMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexTextLogParser implements TextLogParser {

    private static final Pattern LOG_PATTERN = Pattern.compile(
            "^(?<timestamp>\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}) " +
                    "\\[(?<thread>.+?)]\\s+" +
                    "(?<level>INFO|WARN|ERROR|DEBUG|TRACE)\\s+" +
                    "(?<logger>[\\w.$]+)\\s+-\\s+" +
                    "(?<message>.*?)(?:\\s+traceId=(?<traceId>\\S+))?$"
    );

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public RawLogMessage parse(String rawLogLine) {
        Matcher matcher = LOG_PATTERN.matcher(rawLogLine);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("지원하지 않는 로그 형식입니다. rawLogLine=" + rawLogLine);
        }

        String timestampText = matcher.group("timestamp");
        String thread = matcher.group("thread");
        String level = matcher.group("level");
        String logger = matcher.group("logger");
        String message = matcher.group("message");
        String traceId = matcher.group("traceId");

        LocalDateTime timestamp = parseTimestamp(timestampText);

        return new RawLogMessage(
                timestamp,
                level,
                logger,
                thread,
                message,
                traceId,
                rawLogLine
        );
    }

    private LocalDateTime parseTimestamp(String timestampText) {
        try {
            return LocalDateTime.parse(timestampText, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("로그 timestamp 파싱에 실패했습니다. timestamp=" + timestampText, e);
        }
    }
}
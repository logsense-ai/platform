package com.logsense.platform.ingest.service;

import com.logsense.platform.common.exception.CustomException;
import com.logsense.platform.ingest.dto.LogIngestRequest;
import com.logsense.platform.ingest.dto.RawLogMessage;
import com.logsense.platform.ingest.parser.TextLogParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class LogIngestServiceImplTest {

    @Mock
    private TextLogParser textLogParser;

    @InjectMocks
    private LogIngestServiceImpl logIngestService;

    @Test
    @DisplayName("로그 적재 요청이 들어오면 parser를 호출한다")
    void ingest_success() {

        // given
        LogIngestRequest request = createRequest();
        RawLogMessage parsed = createParsedLog();

        given(textLogParser.parse(request.rawLogLine()))
                .willReturn(parsed);

        // when
        RawLogMessage result = logIngestService.ingest(request);

        // then
        assertThat(result).isEqualTo(parsed);

        then(textLogParser)
                .should()
                .parse(request.rawLogLine());
    }

    @Test
    @DisplayName("rawLogLine이 비어 있으면 예외가 발생한다")
    void ingest_fail_when_rawLogLine_is_blank() {

        // given
        LogIngestRequest request = new LogIngestRequest("");

        // when & then
        assertThatThrownBy(() -> logIngestService.ingest(request))
                .isInstanceOf(CustomException.class)
                .hasMessage("rawLogLine은 비어 있을 수 없습니다.");
    }

    @Test
    @DisplayName("요청이 null이면 예외가 발생한다")
    void ingest_fail_when_request_is_null() {

        // when & then
        assertThatThrownBy(() -> logIngestService.ingest(null))
                .isInstanceOf(CustomException.class)
                .hasMessage("잘못된 로그 요청입니다.");
    }

    private LogIngestRequest createRequest() {
        return new LogIngestRequest(
                "2026-03-26 10:15:30 ERROR user-service [main] NullPointerException"
        );
    }

    private RawLogMessage createParsedLog() {

        String rawLine =
                "2026-03-26 10:15:30 ERROR user-service [main] NullPointerException";

        return new RawLogMessage(
                LocalDateTime.of(2026,3,26,10,15,30),
                "ERROR",
                "user-service",
                "main",
                "NullPointerException",
                "trace-1234",
                rawLine
        );
    }
}
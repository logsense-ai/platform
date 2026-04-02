package com.logsense.platform.ingest.parser;

import com.logsense.platform.ingest.dto.RawLogMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegexTextLogParserTest {

    private final RegexTextLogParser parser = new RegexTextLogParser();

    @Test
    @DisplayName("정상 로그 한 줄을 파싱한다")
    void parse_success() {
        // given
        String rawLogLine = "2026-03-23 13:30:01.000 [http-nio-8080-exec-1] ERROR com.app.order.OrderService - NullPointerException while creating order traceId=t-3000";

        // when
        RawLogMessage result = parser.parse(rawLogLine);

        // then
        assertThat(result.timestamp()).isEqualTo(LocalDateTime.of(2026, 3, 23, 13, 30, 1));
        assertThat(result.thread()).isEqualTo("http-nio-8080-exec-1");
        assertThat(result.level()).isEqualTo("ERROR");
        assertThat(result.logger()).isEqualTo("com.app.order.OrderService");
        assertThat(result.message()).isEqualTo("NullPointerException while creating order");
        assertThat(result.traceId()).isEqualTo("t-3000");
        assertThat(result.rawLine()).isEqualTo(rawLogLine);
    }

    @Test
    @DisplayName("traceId가 없는 로그도 파싱한다")
    void parse_success_withoutTraceId() {
        // given
        String rawLogLine = "2026-03-23 13:03:00.000 [http-nio-8080-exec-4] INFO com.app.user.UserService - User login success";

        // when
        RawLogMessage result = parser.parse(rawLogLine);

        // then
        assertThat(result.timestamp()).isEqualTo(LocalDateTime.of(2026, 3, 23, 13, 3, 0));
        assertThat(result.thread()).isEqualTo("http-nio-8080-exec-4");
        assertThat(result.level()).isEqualTo("INFO");
        assertThat(result.logger()).isEqualTo("com.app.user.UserService");
        assertThat(result.message()).isEqualTo("User login success");
        assertThat(result.traceId()).isNull();
    }

    @Test
    @DisplayName("지원하지 않는 로그 형식이면 예외가 발생한다")
    void parse_fail_invalidFormat() {
        // given
        String rawLogLine = "invalid log format";

        // when // then
        assertThatThrownBy(() -> parser.parse(rawLogLine))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("지원하지 않는 로그 형식입니다");
    }
}
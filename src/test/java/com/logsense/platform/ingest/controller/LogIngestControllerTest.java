package com.logsense.platform.ingest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.logsense.platform.common.exception.CustomException;
import com.logsense.platform.common.exception.ErrorCode;
import com.logsense.platform.common.exception.GlobalExceptionHandler;
import com.logsense.platform.ingest.dto.LogIngestRequest;
import com.logsense.platform.ingest.dto.RawLogMessage;
import com.logsense.platform.ingest.service.LogIngestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LogIngestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private LocalValidatorFactoryBean validator;

    @Mock
    private LogIngestService logIngestService;

    @InjectMocks
    private LogIngestController logIngestController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(logIngestController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    @DisplayName("정상적인 로그 적재 요청이 들어오면 200 OK를 반환한다")
    void ingestLog_success() throws Exception {

        // given
        LogIngestRequest request = createValidRequest();
        RawLogMessage response = createParsedLog();

        given(logIngestService.ingest(any(LogIngestRequest.class)))
                .willReturn(response);

        // when & then
        mockMvc.perform(post("/api/v1/logs/ingest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("로그 수집 및 파싱이 완료되었습니다."))
                .andExpect(jsonPath("$.data.timestamp").value("2026-03-26T10:15:30"))
                .andExpect(jsonPath("$.data.level").value("ERROR"))
                .andExpect(jsonPath("$.data.logger").value("user-service"))
                .andExpect(jsonPath("$.data.thread").value("main"))
                .andExpect(jsonPath("$.data.message").value("NullPointerException"))
                .andExpect(jsonPath("$.data.traceId").value("trace-1234"))
                .andExpect(jsonPath("$.data.rawLine").value("2026-03-26 10:15:30 ERROR user-service [main] NullPointerException"));

        then(logIngestService).should()
                .ingest(any(LogIngestRequest.class));
    }

    @Test
    @DisplayName("rawLogLine이 비어 있으면 400 Bad Request를 반환한다")
    void ingestLog_fail_when_rawLogLine_is_blank() throws Exception {

        // given
        LogIngestRequest request = new LogIngestRequest("");

        // when & then
        mockMvc.perform(post("/api/v1/logs/ingest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("rawLogLine은 비어 있을 수 없습니다."));
    }

    @Test
    @DisplayName("rawLogLine이 누락되면 400 Bad Request를 반환한다")
    void ingestLog_fail_when_rawLogLine_is_null() throws Exception {

        // given
        String requestBody = """
                {
                }
                """;

        // when & then
        mockMvc.perform(post("/api/v1/logs/ingest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    @DisplayName("서비스에서 CustomException이 발생하면 예외 응답을 반환한다")
    void ingestLog_fail_when_service_throws_custom_exception() throws Exception {

        // given
        LogIngestRequest request = createValidRequest();

        given(logIngestService.ingest(any(LogIngestRequest.class)))
                .willThrow(new CustomException(ErrorCode.EMPTY_RAW_LOG_LINE));

        // when & then
        mockMvc.perform(post("/api/v1/logs/ingest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("rawLogLine은 비어 있을 수 없습니다."));
    }

    private LogIngestRequest createValidRequest() {
        return new LogIngestRequest(
                "2026-03-26 10:15:30 ERROR user-service [main] NullPointerException"
        );
    }

    private RawLogMessage createParsedLog() {
        return new RawLogMessage(
                LocalDateTime.of(2026, 3, 26, 10, 15, 30),
                "ERROR",
                "user-service",
                "main",
                "NullPointerException",
                "trace-1234",
                "2026-03-26 10:15:30 ERROR user-service [main] NullPointerException"
        );
    }
}
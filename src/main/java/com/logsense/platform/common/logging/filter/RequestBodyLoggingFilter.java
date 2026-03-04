package com.logsense.platform.common.logging.filter;

import com.logsense.platform.common.logging.constants.LoggingConstants;
import com.logsense.platform.common.logging.service.LogEventService;
import com.logsense.platform.common.logging.util.LoggingFilterUtils;
import com.logsense.platform.common.logging.util.LoggingJsonUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestBodyLoggingFilter extends OncePerRequestFilter {

    private final LogEventService logEventService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return LoggingFilterUtils.shouldSkipLogging(request);
    }

    /**
     * 요청 body를 캐싱하기 위해 ContentCachingRequestWrapper로 감싼다.
     * body는 실제로 읽힌 이후 캐시에 저장되므로 filterChain 이후에 조회해야 한다.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        ContentCachingRequestWrapper wrapped = new ContentCachingRequestWrapper(request);

        try {
            filterChain.doFilter(wrapped, response);
        } finally {
            logJsonRequestBodyIfPresent(wrapped);
        }
    }

    /**
     * JSON 요청 body가 존재할 때만 로그를 남긴다.
     */
    private void logJsonRequestBodyIfPresent(ContentCachingRequestWrapper request) {

        String contentType = request.getContentType();
        if (contentType == null) return;

        if (!contentType.toLowerCase().contains(MediaType.APPLICATION_JSON_VALUE)) return;

        byte[] cached = request.getContentAsByteArray();
        if (cached == null || cached.length == 0) return;

        String traceId = MDC.get(LoggingConstants.MDC_TRACE_ID_KEY);
        String body = new String(cached, StandardCharsets.UTF_8);
        body = LoggingJsonUtils.toCompactJson(body);

        log.info("[REQ] {} {} body={}", request.getMethod(), request.getRequestURI(), body);

        // 이벤트 발행
        logEventService.log(
                "INFO",
                "RequestBodyLoggingFilter",
                String.format("[REQ] %s %s\nBody: %s",
                        request.getMethod(),
                        request.getRequestURI(),
                        body),
                traceId
        );
    }
}
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
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseBodyLoggingFilter extends OncePerRequestFilter {

    private static final int MAX_LOG_BODY_BYTES = 8 * 1024;

    private final LogEventService logEventService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return LoggingFilterUtils.shouldSkipLogging(request);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(request, wrappedResponse);
        } finally {
            try {
                logJsonResponseBodyIfPresent(request, wrappedResponse);
            } finally {
                wrappedResponse.copyBodyToResponse();
            }
        }
    }

    private void logJsonResponseBodyIfPresent(HttpServletRequest request, ContentCachingResponseWrapper response) {
        String contentType = response.getContentType();
        if (contentType == null) return;

        if (!contentType.toLowerCase().contains(MediaType.APPLICATION_JSON_VALUE)) return;

        byte[] cached = response.getContentAsByteArray();
        if (cached == null || cached.length == 0) return;

        boolean truncated = cached.length > MAX_LOG_BODY_BYTES;
        int length = Math.min(cached.length, MAX_LOG_BODY_BYTES);

        String rawBody = new String(cached, 0, length, StandardCharsets.UTF_8);

        String compactBody = LoggingJsonUtils.toCompactJson(rawBody);

        if (truncated) {
            compactBody = compactBody + " ...(truncated)";
        }

        String traceId = MDC.get(LoggingConstants.MDC_TRACE_ID_KEY);
        int status = response.getStatus();

        log.info("[RES] {} {} status={} body={}",
                request.getMethod(), request.getRequestURI(), status, compactBody);

        //이벤트 발행
        logEventService.log(
                "INFO",
                "ResponseBodyLoggingFilter",
                String.format("[RES] %s %s status=%d body=%s",
                        request.getMethod(), request.getRequestURI(), status, compactBody),
                traceId
        );
    }
}
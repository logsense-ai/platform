package com.logsense.platform.common.logging.filter;

import com.logsense.platform.common.logging.constants.LoggingConstants;
import com.logsense.platform.common.logging.util.LoggingFilterUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class TraceIdFilter extends OncePerRequestFilter {

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

        String traceId = UUID.randomUUID().toString();

        MDC.put(LoggingConstants.MDC_TRACE_ID_KEY, traceId);

        request.setAttribute(LoggingConstants.REQ_TRACE_ID_ATTR, traceId);

        response.setHeader(LoggingConstants.TRACE_ID_HEADER, traceId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(LoggingConstants.MDC_TRACE_ID_KEY);
        }
    }
}
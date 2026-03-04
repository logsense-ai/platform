package com.logsense.platform.common.logging.util;

import com.logsense.platform.common.logging.constants.LoggingExcludes;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 로깅 필터에서 공통적으로 사용하는 요청 필터링 로직
 */
public final class LoggingFilterUtils {

    private LoggingFilterUtils() {}

    /**
     * 로깅을 적용하지 않을 요청인지 판단한다.
     */
    public static boolean shouldSkipLogging(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // API prefix가 아니면 제외
        if (!uri.startsWith(LoggingExcludes.API_PREFIX)) {
            return true;
        }

        // actuator, swagger 등 시스템 endpoint 제외
        if (LoggingExcludes.URL_PREFIXES.stream().anyMatch(uri::startsWith)) {
            return true;
        }

        // OPTIONS / HEAD 요청 제외
        return "OPTIONS".equalsIgnoreCase(method)
                || "HEAD".equalsIgnoreCase(method);
    }
}
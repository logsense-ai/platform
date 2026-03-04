package com.logsense.platform.common.logging.constants;

import java.util.List;

/**
 * 로깅 대상에서 제외할 URL prefix 관리
 */
public final class LoggingExcludes {

    private LoggingExcludes() {}

    /** 로그 찍을 API prefix **/
    public static final String API_PREFIX = "/api";

    /** 로깅 제외할 prefix 목록 **/
    public static final List<String> URL_PREFIXES = List.of(
            "/actuator",
            "/swagger",
            "/v3/api-docs",
            "/favicon.ico"
    );

}
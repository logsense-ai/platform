package com.logsense.platform.common.logging.constants;

public final class LoggingConstants {

    private LoggingConstants() {}

    /** MDC에 traceId를 넣을 때 사용하는 key */
    public static final String MDC_TRACE_ID_KEY = "traceId";

    /** HttpServletRequest attribute로도 traceId를 넣어두고 싶을 때 사용하는 key */
    public static final String REQ_TRACE_ID_ATTR = "traceId";

    /** 응답 헤더로 traceId를 내려줄 때 사용하는 헤더명 */
    public static final String TRACE_ID_HEADER = "X-Trace-Id";
}
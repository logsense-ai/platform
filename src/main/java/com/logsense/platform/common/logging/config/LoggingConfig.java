package com.logsense.platform.common.logging.config;

import com.logsense.platform.common.logging.filter.RequestBodyLoggingFilter;
import com.logsense.platform.common.logging.filter.ResponseBodyLoggingFilter;
import com.logsense.platform.common.logging.filter.TraceIdFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 실행 순서
 * 1. TraceIdFilter             → 요청마다 traceId 생성 및 MDC 세팅
 * 2. RequestBodyLoggingFilter  → 요청 JSON body 로깅
 * 3. ResponseBodyLoggingFilter → 응답 JSON body 로깅
 */
@Configuration
@RequiredArgsConstructor
public class LoggingConfig {

    private final TraceIdFilter traceIdFilter;
    private final RequestBodyLoggingFilter requestBodyLoggingFilter;
    private final ResponseBodyLoggingFilter responseBodyLoggingFilter;

    /**
     * traceId 생성 필터 등록
     *
     * 가장 먼저 실행되어야 이후 모든 로그에서 동일한 traceId를 사용할 수 있다.
     */
    @Bean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilterRegistration() {
        FilterRegistrationBean<TraceIdFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(traceIdFilter);
        bean.setOrder(1);
        bean.addUrlPatterns("/api/*");
        return bean;
    }

    /**
     * Request Body 로깅 필터 등록
     *
     * TraceIdFilter 이후 실행되어 traceId가 MDC에 저장된 상태에서
     * 요청 JSON body를 로깅할 수 있도록 한다.
     */
    @Bean
    public FilterRegistrationBean<RequestBodyLoggingFilter> requestBodyLoggingFilterRegistration() {
        FilterRegistrationBean<RequestBodyLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(requestBodyLoggingFilter);
        bean.setOrder(2);
        return bean;
    }

    /**
     * Response Body 로깅 필터 등록
     *
     * 컨트롤러 실행 이후 응답 JSON body를 로깅한다.
     * RequestBodyLoggingFilter 이후 실행되어 요청/응답 로그가
     * 같은 traceId 기준으로 연결된다.
     */
    @Bean
    public FilterRegistrationBean<ResponseBodyLoggingFilter> responseBodyLoggingFilterRegistration() {
        FilterRegistrationBean<ResponseBodyLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(responseBodyLoggingFilter);
        bean.setOrder(3);
        return bean;
    }
}
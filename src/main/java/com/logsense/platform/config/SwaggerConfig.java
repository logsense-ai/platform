package com.logsense.platform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("LogSense-AI Platform")
                .description("생성된 장애는 과거 유사 사례와 로그 근거를 함께 분석하여 원인 후보와 대응 방안을 제시하며, " +
                        "운영자가 빠르게 문제를 파악할 수 있도록 돕는 AI 기반 장애 분석 시스템입니다.")
                .version("v1.0.0");

        return new OpenAPI().info(info);
    }

    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/api/**")
                .build();
    }

}


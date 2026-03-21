package com.logsense.platform.log.entity;

import com.logsense.platform.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "log_event")
@Comment("정규화된 로그 이벤트")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LogEvent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("로그 이벤트 ID")
    private Long id;

    @Comment("로그 발생 시각")
    private LocalDateTime occurredAt;

    @Comment("서비스명")
    @Column(length = 100)
    private String serviceName;

    @Comment("환경")
    @Column(length = 30)
    private String env;

    @Comment("로그 레벨")
    @Column(length = 20)
    private String level;

    @Comment("이벤트 타입")
    @Column(length = 50)
    private String eventType;

    @Column(columnDefinition = "TEXT")
    @Comment("로그 메시지")
    private String message;

    @Comment("예외 클래스")
    @Column(length = 200)
    private String exceptionClass;

    @Column(columnDefinition = "TEXT")
    @Comment("스택트레이스")
    private String stacktrace;

    @Comment("trace ID")
    @Column(length = 100)
    private String traceId;

    @Comment("시그니처 해시")
    @Column(length = 128)
    private String signatureHash;

    @Column(columnDefinition = "jsonb")
    @Comment("원본 로그 JSON")
    private String rawJson;
}
package com.logsense.platform.incident.entity;

import com.logsense.platform.common.BaseTimeEntity;
import com.logsense.platform.incident.enums.AnalyzeRequestStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "analyze_request")
@Comment("AI 분석 요청 관리 테이블")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AnalyzeRequest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("분석 요청 ID")
    private Long id;

    @Comment("Incident ID")
    private Long incidentId;

    @Enumerated(EnumType.STRING)
    @Comment("요청 상태")
    @Column(length = 30)
    private AnalyzeRequestStatus requestStatus;

    @Comment("재시도 횟수")
    private Integer retryCount;

    @Column(columnDefinition = "TEXT")
    @Comment("마지막 에러")
    private String lastError;

    @Comment("요청 시각")
    private LocalDateTime requestedAt;

    @Comment("처리 완료 시각")
    private LocalDateTime processedAt;
}
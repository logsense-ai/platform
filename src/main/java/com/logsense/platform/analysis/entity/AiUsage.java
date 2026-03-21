package com.logsense.platform.analysis.entity;

import com.logsense.platform.analysis.enums.UsageStatus;
import com.logsense.platform.common.BaseTimeEntity;
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
@Table(name = "ai_usage")
@Comment("AI 호출 비용 및 사용량 추적 테이블")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AiUsage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("사용량 추적 ID")
    private Long id;

    @Comment("Incident ID")
    private Long incidentId;

    @Comment("AI 분석 결과 ID")
    private Long analysisId;

    @Comment("사용 모델명")
    @Column(length = 100)
    private String model;

    @Comment("입력 토큰 수")
    private Integer inputTokens;

    @Comment("출력 토큰 수")
    private Integer outputTokens;

    @Comment("총 토큰 수")
    private Integer totalTokens;

    @Comment("추정 비용")
    private Double estimatedCost;

    @Comment("응답 시간(ms)")
    private Integer latencyMs;

    @Enumerated(EnumType.STRING)
    @Comment("호출 상태")
    @Column(length = 30)
    private UsageStatus status;

    @Comment("요청 시각")
    private LocalDateTime requestedAt;
}
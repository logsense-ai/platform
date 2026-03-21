package com.logsense.platform.analysis.entity;

import com.logsense.platform.analysis.enums.AiAnalysisStatus;
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

@Entity
@Table(name = "ai_analysis")
@Comment("AI 분석 결과 테이블")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AiAnalysis extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("AI 분석 결과 ID")
    private Long id;

    @Comment("Incident ID")
    private Long incidentId;

    @Enumerated(EnumType.STRING)
    @Comment("분석 상태")
    @Column(length = 30)
    private AiAnalysisStatus status;

    @Column(columnDefinition = "jsonb")
    @Comment("AI 분석 결과 JSON")
    private String analysisJson;

    @Comment("신뢰도 점수")
    private Double confidence;

    @Comment("사용 모델명")
    @Column(length = 100)
    private String modelName;

    @Comment("프롬프트 버전")
    @Column(length = 50)
    private String promptVersion;

    @Column(columnDefinition = "TEXT")
    @Comment("에러 메시지")
    private String errorMessage;
}
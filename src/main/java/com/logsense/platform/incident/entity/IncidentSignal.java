package com.logsense.platform.incident.entity;

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
@Table(name = "incident_signal")
@Comment("Incident 감지 근거 데이터")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class IncidentSignal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("근거 데이터 ID")
    private Long id;

    @Comment("Incident ID")
    private Long incidentId;

    @Comment("에러 발생 건수")
    private Integer errorCount;

    @Comment("Baseline 평균")
    private Double baselineAvg;

    @Comment("증가 배수")
    private Double increaseRatio;

    @Comment("윈도우 시작 시각")
    private LocalDateTime windowStart; //장애 감지된 시각 저장

    @Comment("윈도우 종료 시각")
    private LocalDateTime windowEnd; // 장애 감지된 마지막 시각 저장

    @Column(columnDefinition = "jsonb")
    @Comment("샘플 로그")
    private String sampleLogs;

    @Column(columnDefinition = "jsonb")
    @Comment("traceId 목록")
    private String traceIds;

    @Comment("적용 규칙")
    @Column(length = 100)
    private String ruleName;
}
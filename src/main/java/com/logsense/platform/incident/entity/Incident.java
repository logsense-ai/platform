package com.logsense.platform.incident.entity;

import com.logsense.platform.common.BaseTimeEntity;
import com.logsense.platform.incident.enums.IncidentStatus;
import com.logsense.platform.incident.enums.Severity;
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
@Table(name = "incident")
@Comment("장애(Incident) 본체 테이블")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Incident extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Incident 고유 ID")
    private Long id;

    @Comment("장애 발생 서비스명")
    @Column(length = 100)
    private String serviceName;

    @Enumerated(EnumType.STRING)
    @Comment("Incident 상태")
    @Column(length = 30)
    private IncidentStatus status;

    @Enumerated(EnumType.STRING)
    @Comment("장애 심각도")
    @Column(length = 20)
    private Severity severity;

    @Column(columnDefinition = "TEXT")
    @Comment("장애 요약")
    private String summary;

    @Comment("에러 시그니처 해시")
    @Column(length = 128)
    private String signatureHash;

    @Comment("장애 시작 시각")
    private LocalDateTime startedAt;

    @Comment("장애 종료 시각")
    private LocalDateTime endedAt;
}
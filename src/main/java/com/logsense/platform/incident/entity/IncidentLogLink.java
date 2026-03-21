package com.logsense.platform.incident.entity;

import com.logsense.platform.common.BaseTimeEntity;
import com.logsense.platform.incident.enums.LogRole;
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
@Table(name = "incident_log_link")
@Comment("Incident와 로그 매핑 테이블")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class IncidentLogLink extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("매핑 ID")
    private Long id;

    @Comment("Incident ID")
    private Long incidentId;

    @Comment("로그 이벤트 ID")
    private Long logEventId;

    @Enumerated(EnumType.STRING)
    @Comment("로그 역할")
    @Column(length = 30)
    private LogRole logRole;
}
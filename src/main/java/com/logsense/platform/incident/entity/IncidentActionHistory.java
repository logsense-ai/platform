package com.logsense.platform.incident.entity;

import com.logsense.platform.common.BaseTimeEntity;
import com.logsense.platform.incident.enums.ActionType;
import com.logsense.platform.incident.enums.ActorType;
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
@Table(name = "incident_action_history")
@Comment("Incident 상태 변경 이력")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class IncidentActionHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("이력 ID")
    private Long id;

    @Comment("Incident ID")
    private Long incidentId;

    @Enumerated(EnumType.STRING)
    @Comment("조치 유형")
    @Column(length = 30)
    private ActionType actionType;

    @Comment("이전 상태")
    @Column(length = 30)
    private String fromStatus;

    @Comment("변경 상태")
    @Column(length = 30)
    private String toStatus;

    @Enumerated(EnumType.STRING)
    @Comment("수행 주체")
    @Column(length = 30)
    private ActorType actorType;

    @Comment("수행자 ID")
    @Column(length = 100)
    private String actorId;

    @Column(columnDefinition = "TEXT")
    @Comment("메모")
    private String memo; //상태 변경이 왜 일어났는지 기록
}
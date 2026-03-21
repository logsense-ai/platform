package com.logsense.platform.incident.entity;

import com.logsense.platform.common.BaseTimeEntity;
import com.logsense.platform.incident.enums.DedupType;
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
@Table(name = "dedup_key")
@Comment("중복 처리(멱등성) 관리 테이블")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DedupKey extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("멱등 키 ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Comment("중복 키 유형")
    @Column(length = 30)
    private DedupType dedupType;

    @Comment("중복 방지 키")
    @Column(length = 300)
    private String dedupKey;

    @Comment("연결 대상 ID")
    private Long targetId;

    @Comment("만료 시각")
    private LocalDateTime expiresAt;
}
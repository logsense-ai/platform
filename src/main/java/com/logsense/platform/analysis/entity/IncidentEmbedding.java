package com.logsense.platform.analysis.entity;

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

@Entity
@Table(name = "incident_embedding")
@Comment("유사 장애 검색용 임베딩 테이블")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class IncidentEmbedding extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("임베딩 ID")
    private Long id;

    @Comment("Incident ID")
    private Long incidentId;

    @Column(columnDefinition = "vector(1536)")
    @Comment("임베딩 벡터")
    private String embedding;

    @Column(columnDefinition = "TEXT")
    @Comment("임베딩 생성 기준 텍스트")
    private String embeddingText;

    @Comment("임베딩 모델명")
    @Column(length = 100)
    private String modelName;
}
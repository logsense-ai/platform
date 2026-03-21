package com.logsense.platform.common;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    @Comment("생성 시각")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Comment("수정 시각")
    private LocalDateTime updatedAt;
}
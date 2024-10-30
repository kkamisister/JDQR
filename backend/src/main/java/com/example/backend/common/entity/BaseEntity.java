package com.example.backend.common.entity;

import java.time.LocalDateTime;

import com.example.backend.common.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@SuperBuilder
@NoArgsConstructor
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private EntityStatus status;

    @CreatedDate
    @Column(updatable = false, nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;


    @PrePersist
    public void prePersist() {
        this.status = EntityStatus.ACTIVE;
    }
}

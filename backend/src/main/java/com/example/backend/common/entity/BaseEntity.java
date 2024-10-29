package com.example.backend.common.entity;

import com.example.backend.common.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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

    @PrePersist
    public void prePersist() {
        this.status = EntityStatus.ACTIVE;
    }
}

package com.example.backend.common.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	@Column(nullable = false,name = "status")
	private Boolean status;

	@CreatedDate
	@Column(updatable = false,nullable = false,name = "created_at")
	private LocalDateTime creadtedAt;

	@LastModifiedDate
	@Column(nullable = false,name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	public void prePersist(){this.status = true;}
}

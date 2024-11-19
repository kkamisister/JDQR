package com.example.backend.table.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.example.backend.common.entity.BaseEntity;
import com.example.backend.common.enums.EntityStatus;
import com.example.backend.common.enums.UseStatus;
import com.example.backend.table.dto.TableRequest.TableInfo;
import com.fasterxml.jackson.databind.ser.Serializers;

import jakarta.persistence.Id;

@Document(collection = "table")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Table {
	@Id @Field(value = "_id",targetType = FieldType.OBJECT_ID)
	@MongoId
	private String id;
	@Field("restaurant_id")
	private int restaurantId;
	@Field("qr_code")
	private String qrCode;
	@Field("name")
	private String name;

	@Field("people")
	private int people;
	@Field("color")
	private String color;
	@Field("use_status")
	private UseStatus useStatus;

	@Field("status")
	@Enumerated(EnumType.STRING)
	private EntityStatus status;

	@CreatedDate
	@Field("created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Field("updated_at")
	private LocalDateTime updatedAt;

	@LastModifiedDate
	@Field("qr_updated_at")
	private LocalDateTime qrUpdatedAt;

	public static Table of(TableInfo tableInfo,int restaurantId,UseStatus status){
		return Table.builder()
			.name(tableInfo.name())
			.restaurantId(restaurantId)
			.color(tableInfo.color())
			.people(tableInfo.people())
			.useStatus(status)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}

	public void updateQrCode(String newQrCode) {
		this.qrCode = newQrCode;
		this.qrUpdatedAt = LocalDateTime.now(); // qrCode 변경 시 lastUpdated 갱신
	}

	public void updateTable(TableInfo tableInfo){
		this.name = tableInfo.name();
		this.people = tableInfo.people();
		this.color = tableInfo.color();
	}
}

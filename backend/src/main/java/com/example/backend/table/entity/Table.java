package com.example.backend.table.entity;

import java.util.List;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.example.backend.common.enums.UseStatus;
import com.example.backend.table.dto.Grid;
import com.example.backend.table.dto.TableRequest.TableInfo;

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
	private UseStatus status;
	@Field("pos")
	private List<Grid> pos;

	public static Table of(TableInfo tableInfo,int restaurantId,UseStatus status){
		return Table.builder()
			.name(tableInfo.name())
			.restaurantId(restaurantId)
			.color(tableInfo.color())
			.people(tableInfo.people())
			.pos(tableInfo.position())
			.status(status)
			.build();
	}
}

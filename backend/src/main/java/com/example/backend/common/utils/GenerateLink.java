package com.example.backend.common.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.backend.table.entity.Table;

@Component
public class GenerateLink {

	private final String QR_PREFIX = "http://localhost:8080/api/v1/admin/qr";

	public String create(Table table){

		String tableSuffix = UUID.randomUUID().toString();

		return QR_PREFIX+"/"+table.getName()+"/"+tableSuffix;
	}

}

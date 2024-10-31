package com.example.backend.table.repository;

import java.util.Optional;

import com.example.backend.table.entity.Table;

public interface TableRepository {
	Table save(Table table);
	Optional<Table> findById(String id);

	Table updateQrCode(String id,String qrCode);
}

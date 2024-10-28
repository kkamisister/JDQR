package com.example.backend.table.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.example.backend.table.entity.Table;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TableRepositoryImpl implements TableRepository {

	private final MongoTemplate mongoTemplate;

	@Override
	public Table save(Table table) {
		return mongoTemplate.save(table);
	}
}

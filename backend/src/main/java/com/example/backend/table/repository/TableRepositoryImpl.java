package com.example.backend.table.repository;

import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

	@Override
	public Optional<Table> findById(String id) {

		Query query = new Query();

		query.addCriteria(Criteria.where("id").is(id));

		return Optional.ofNullable(mongoTemplate.findOne(query,Table.class));
	}
}

package com.example.backend.table.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

		query.addCriteria(Criteria.where("_id").is(id));

		return Optional.ofNullable(mongoTemplate.findOne(query,Table.class));
	}

	@Override
	public List<Table> findByRestaurantId(int restaurantId) {

		Query query = new Query();

		query.addCriteria(Criteria.where("restaurant_id").is(restaurantId));

		return mongoTemplate.find(query,Table.class);

	}

	@Override
	public Table updateQrCode(String id, String qrCode) {

		Query query = new Query();

		query.addCriteria(Criteria.where("_id").is(id));

		Update update = new Update();
		update.set("qr_code",qrCode);

		return mongoTemplate.findAndModify(query,update,Table.class);
	}
}

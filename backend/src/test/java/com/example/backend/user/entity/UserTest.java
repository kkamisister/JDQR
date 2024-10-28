package com.example.backend.user.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.common.config.QuerydslConfig;
import com.example.backend.user.repository.UserRepository;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@DataJpaTest
@Import({QuerydslConfig.class})
@Slf4j
class UserTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EntityManager em;

	@DisplayName("사용자의 Code를 통해 사용자를 검색할 수 있다")
	@Test
	void findByCode(){

		User user = User.builder()
			.email("sujipark2009@gmail.com")
			.code("ABCDEFG")
			.name("김영표")
			.industry("주식")
			.address("경기도 수원시 영통구 덕영대로")
			.image("sadlkjf3092u0954")
			.registrationNumber("11-6654-42332-45")
			.lat(33.117779)
			.lng(127.6484846)
			.build();

		userRepository.save(user);

		assertThat(user.getCode()).isEqualTo("ABCDEFG");
	}

}
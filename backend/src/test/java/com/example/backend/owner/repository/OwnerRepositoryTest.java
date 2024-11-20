package com.example.backend.owner.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.backend.TestDataGenerator;
import com.example.backend.common.config.QuerydslConfig;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.config.ContainerSupport;
import com.example.backend.owner.entity.Owner;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@DataJpaTest
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class OwnerRepositoryTest {

	@Autowired
	private OwnerRepository ownerRepository;

	private final TestDataGenerator generator = new TestDataGenerator();

	@DisplayName("코드를 사용해서 사용자를 검색할 수 있다")
	@Test
	void findByCode(){

		//given
		Owner owner = Owner.builder()
			.email("sujipark2009@gmail.com")
			.code("ABCDEFG")
			.name("김영표")
			.build();

		ownerRepository.save(owner);

		//when
		Owner findOwner = ownerRepository.findByCode(owner.getCode())
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//then
		assertThat(owner.getCode()).isEqualTo(findOwner.getCode());
		assertThat(owner.getName()).isEqualTo(findOwner.getName());
	}

}
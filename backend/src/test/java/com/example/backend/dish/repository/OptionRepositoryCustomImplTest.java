package com.example.backend.dish.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
import com.example.backend.dish.entity.Choice;
import com.example.backend.dish.entity.Option;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.repository.RestaurantRepository;
import com.example.backend.owner.dto.OptionVo;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@DataJpaTest
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class OptionRepositoryCustomImplTest {

	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private OptionRepository optionRepository;
	@Autowired
	private ChoiceRepository choiceRepository;
	@Autowired
	private OwnerRepository ownerRepository;

	private final TestDataGenerator generator = new TestDataGenerator();

	@DisplayName("식당에 있는 모든 옵션을 조회할 수 있다")
	@Test
	void findAllOptionByRestaurant() {

		Owner owner = Owner.builder()
			.email("sujipark2009@gmail.com")
			.code("ABCDEFG")
			.name("김영표")
			.build();

		Owner savedOwner = ownerRepository.save(owner);

		List<Restaurant> restaurants = generator.generateTestRestaurantList(false);
		for(Restaurant restaurant : restaurants) {
			restaurant.setOwner(savedOwner);
		}
		List<Restaurant> restaurants1 = restaurantRepository.saveAll(restaurants);

		List<Option> options = generator.generateTestOptionList(false);
		for(Option option : options) {
			option.setRestaurant(restaurants1.get(0));
		}
		optionRepository.saveAll(options);

		List<Choice> choices = generator.generateTestChoiceList(false);
		for(int i=0;i<choices.size();i++) {
			Choice choice = choices.get(i);
			choice.setOption(options.get(i));
		}
		choiceRepository.saveAll(choices);

		//when
		List<OptionVo> res = optionRepository.findAllOptionByRestaurant(restaurants1.get(0));

		//then
		assertThat(res)
			.extracting(OptionVo::getOptionName)
			.containsExactly(
				"부위","맛","샷추가","휘핑크림","소스","양념"
			);

	}
}
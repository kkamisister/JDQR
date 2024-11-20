package com.example.backend.common.redis.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.example.backend.common.config.QuerydslConfig;
import com.example.backend.config.ContainerSupport;
import com.example.backend.order.dto.CartDto;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@Import({QuerydslConfig.class})
@SpringBootTest
@Slf4j
class RedisHashRepositoryImplTest extends ContainerSupport {

	@Autowired
	private RedisHashRepositoryImpl redisHashRepository;

	@DisplayName("tableId에 해당하는 장바구니를 삭제할 수 있다")
	@Test
	void removeCartWithTableId() {

		String tableId = "6783qf9hiocj";
		String key = "table::"+tableId;

		Map<Integer,CartDto> cachedCartData = new ConcurrentHashMap<>();

		CartDto cartData = CartDto.builder()
			.userId("6f5f891356")
			.dishId(4)
			.dishName("더블QPC")
			.dishCategoryId(1)
			.dishCategoryName("햄버거")
			.choiceIds(List.of(1,2))
			.price(7400)
			.quantity(2)
			.orderedAt(null)
			.build();

		log.warn("cartData : {}",cartData);

		cachedCartData.put(cartData.hashCode(),cartData);
		redisHashRepository.saveHashData(key, cartData.getUserId(), cachedCartData,20L);

		//when
		Map<Integer, CartDto> cartDatas = redisHashRepository.getCartDatas(key, cartData.getUserId());

		//then
		Assertions.assertThat(cartDatas.get(cartData.hashCode()))
			.extracting(CartDto::getChoiceIds)
			.isEqualTo(List.of(1,2));

	}
}
package com.example.backend.dish.entity;

import com.example.backend.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DishOptionGroup extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dish_id")
	private Dish dish;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_group_id")
	private OptionGroup optionGroup;

	public static DishOptionGroup of(Dish dish, OptionGroup optionGroup) {

		return DishOptionGroup.builder()
			.dish(dish)
			.optionGroup(optionGroup)
			.build();

	}
}

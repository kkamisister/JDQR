package com.example.backend.dish.entity;


import com.example.backend.common.entity.BaseEntity;
import com.example.backend.dish.dto.ChoiceDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "choices")
public class Choice extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_id")
	private Option option;

	@Column(name = "name")
	private String name;
	@Column(name = "price")
	private Integer price;

	public static Choice of(ChoiceDto choiceDto, Option option) {
		return Choice.builder()
			.name(choiceDto.getChoiceName())
			.price(choiceDto.getPrice())
			.option(option)
			.build();
	}
}

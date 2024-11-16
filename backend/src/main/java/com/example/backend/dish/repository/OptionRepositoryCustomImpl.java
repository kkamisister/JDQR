package com.example.backend.dish.repository;

import com.example.backend.common.enums.EntityStatus;
import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.owner.dto.OptionVo;
import com.querydsl.core.types.Projections;

import static com.example.backend.common.enums.EntityStatus.*;
import static com.example.backend.dish.entity.QOption.option;
import static com.example.backend.dish.entity.QChoice.choice;

import java.util.List;

public class OptionRepositoryCustomImpl extends Querydsl4RepositorySupport implements OptionRepositoryCustom {
    @Override
    public List<OptionVo> findAllOptionByRestaurant(Restaurant restaurant) {
        return select(Projections.constructor(OptionVo.class, option.id, option.name, option.maxChoiceCount, option.mandatory, choice.id, choice.name, choice.price))
            .from(option)
            .leftJoin(option.choices, choice)
            .where(
                option.restaurant.eq(restaurant),
                option.status.eq(ACTIVE),
                choice.isNull().or(choice.status.eq(ACTIVE))
            )
            .fetch();
    }

    @Override
    public List<OptionVo> findOptionByOptionId(Integer optionId) {
        return select(Projections.constructor(OptionVo.class, option.id, option.name, option.maxChoiceCount, option.mandatory, choice.id, choice.name, choice.price))
            .from(choice)
            .join(choice.option, option)
            .where(
                option.id.eq(optionId),
                option.status.eq(ACTIVE),
                choice.status.eq(ACTIVE)
            )
            .fetch();
    }
}

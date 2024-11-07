package com.example.backend.dish.repository;

import com.example.backend.common.repository.Querydsl4RepositorySupport;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.owner.dto.OptionVo;
import com.querydsl.core.types.Projections;

import static com.example.backend.dish.entity.QOption.option;
import static com.example.backend.dish.entity.QChoice.choice;

import java.util.List;

public class OptionRepositoryCustomImpl extends Querydsl4RepositorySupport implements OptionRepositoryCustom {
    @Override
    public List<OptionVo> findAllOptionByRestaurant(Restaurant restaurant) {
        return select(Projections.constructor(OptionVo.class, option.id, option.name, option.maxChoiceCount, option.mandatory, choice.id, choice.name, choice.price))
            .from(choice)
            .join(choice.option, option)
            .where(option.restaurant.eq(restaurant))
            .fetch();
    }
}

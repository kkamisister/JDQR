package com.example.backend.dish.repository;

import com.example.backend.etc.entity.Restaurant;
import com.example.backend.owner.dto.OptionVo;

import java.util.List;

public interface OptionRepositoryCustom {
    List<OptionVo> findAllOptionByRestaurant(Restaurant restaurant);

    List<OptionVo> findOptionByOptionId(Integer optionId);
}

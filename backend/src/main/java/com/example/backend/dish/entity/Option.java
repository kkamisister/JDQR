package com.example.backend.dish.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.common.entity.BaseEntity;
import com.example.backend.dish.dto.OptionDto;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.owner.dto.OwnerRequest;
import com.example.backend.owner.dto.OwnerRequest.OptionRequestDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "options")
public class Option extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "name")
    private String name;

    @Column(name = "max_choice_count")
    private Integer maxChoiceCount;

    @Column(name = "mandatory")
    private Boolean mandatory;

    @Builder.Default
    @OneToMany(mappedBy = "option",cascade = CascadeType.REMOVE)
    private List<Choice> choices = new ArrayList<>();

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public static Option of(Restaurant restaurant,OptionRequestDto optionDto){
        return Option.builder()
            .restaurant(restaurant)
            .name(optionDto.optionName())
            .maxChoiceCount(optionDto.maxChoiceCount())
            .mandatory(optionDto.isMandatory())
            .build();
    }

}

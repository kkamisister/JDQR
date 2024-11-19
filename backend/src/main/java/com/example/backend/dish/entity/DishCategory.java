package com.example.backend.dish.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.common.entity.BaseEntity;
import com.example.backend.etc.entity.Restaurant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dish_categories")
public class DishCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Builder.Default
    @OneToMany(mappedBy = "dishCategory")
    private List<Dish> dishes = new ArrayList<>();

    private String name;

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}


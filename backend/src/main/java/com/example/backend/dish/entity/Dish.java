package com.example.backend.dish.entity;

import com.example.backend.common.entity.BaseEntity;
import com.example.backend.dish.dto.DishRequest;
import com.example.backend.dish.dto.DishRequest.DishInfo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dish")
public class Dish extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_category_id")
    private DishCategory dishCategory;

    private String name;
    private Integer price;
    private String description;

    @Column(name = "image")
    private String image;

    public static Dish of(DishInfo dishInfo,DishCategory dishCategory,String imageUrl){
        return Dish.builder()
            .dishCategory(dishCategory)
            .name(dishInfo.dishName())
            .price(dishInfo.price())
            .description(dishInfo.description())
            .image(imageUrl)
            .build();
    }

    @Override
    public String toString() {
        return "Dish{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", description='" + description + '\'' +
            ", image='" + image + '\'' +
            '}';
    }
}

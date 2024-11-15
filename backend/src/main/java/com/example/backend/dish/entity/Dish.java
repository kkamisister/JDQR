package com.example.backend.dish.entity;

import java.util.List;

import com.example.backend.common.entity.BaseEntity;
import com.example.backend.dish.dto.DishRequest;
import com.example.backend.dish.dto.DishRequest.DishInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    @Column(name = "tags")
    private String tags;
    /**
     *
     * 사용자로부터 입력받은 tags를 Json 문자열 형식으로 저장하는 메서드
     * @param tags
     */
    public void setTags(List<String> tags){
        ObjectMapper objectMapper = new ObjectMapper();
        String tagsJson = null;

        try {
            tagsJson = objectMapper.writeValueAsString(tags);
        } catch (JsonProcessingException e) {
            tagsJson = "[]";
        }
        this.tags = tagsJson;
    }

    public static Dish of(DishInfo dishInfo,DishCategory dishCategory,String imageUrl){

        ObjectMapper objectMapper = new ObjectMapper();
        String tagsJson = null;

        try {
            tagsJson = objectMapper.writeValueAsString(dishInfo.tags());
        } catch (JsonProcessingException e) {
            tagsJson = "[]";
        }

        return Dish.builder()
            .dishCategory(dishCategory)
            .name(dishInfo.dishName())
            .price(dishInfo.price())
            .description(dishInfo.description())
            .image(imageUrl)
            .tags(tagsJson)
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

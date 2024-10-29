package com.example.backend.etc.entity;


import com.example.backend.common.entity.BaseEntity;
import com.example.backend.common.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "restaurant_categories")
public class RestaurantCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "category_type")
    private CategoryType categoryType;
}

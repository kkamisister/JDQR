package com.example.backend.order.entity;

import com.example.backend.common.entity.BaseEntity;
import com.example.backend.dish.entity.Choice;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item_choices")
public class OrderItemChoice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice_id")
    private Choice choice;

    public static OrderItemChoice of(OrderItem orderItem, Choice choice) {
        return OrderItemChoice.builder()
            .orderItem(orderItem)
            .choice(choice)
            .build();
    }
}

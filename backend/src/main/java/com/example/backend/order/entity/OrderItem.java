package com.example.backend.order.entity;

import com.example.backend.common.entity.BaseEntity;
import com.example.backend.dish.entity.Dish;
import com.example.backend.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_items")
@ToString
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    // 주문수량
    private Integer quantity;

    // 결제수량
    @Column(name = "paid_quantity")
    private Integer paidQuantity;

    @Column(name = "order_price")
    private Integer orderPrice;

    @Column(name = "ordered_at")
    private LocalDateTime orderedAt;

    @Builder.Default
    @OneToMany(mappedBy = "orderItem")
    private List<OrderItemChoice> orderItemChoices = new ArrayList<>();

}

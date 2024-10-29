package com.example.backend.order.entity;

import com.example.backend.common.entity.BaseEntity;
import com.example.backend.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "menu_cnt")
    private Integer menuCnt;
    private OrderStatus orderStatus;
}
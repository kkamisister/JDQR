package com.example.backend.order.entity;

import com.example.backend.common.entity.BaseEntity;
import com.example.backend.order.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payments")
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_order_id")
    private ParentOrder parentOrder;

    // 결제 금액
    private Integer amount;

    // 토스 결제 api 사용을 위한 주문번호
    @Column(name = "toss_order_id")
    private String tossOrderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;
}

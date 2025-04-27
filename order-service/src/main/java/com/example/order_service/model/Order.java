package com.example.order_service.model;

import com.example.order_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;

    private int quantity;

    private double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Instant createdAt;
}

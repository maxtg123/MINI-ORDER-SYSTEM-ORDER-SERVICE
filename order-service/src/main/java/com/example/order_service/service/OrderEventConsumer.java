package com.example.order_service.service;
import com.example.order_service.enums.OrderStatus;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "inventory-events", groupId = "order-group")
    public void consumeInventoryEvent(String message) {
        log.info("Received inventory event: {}", message);

        // Giả sử message là: "orderId:status" (ví dụ: "1:APPROVED")
        String[] parts = message.split(":");
        if (parts.length == 2) {
            Long orderId = Long.parseLong(parts[0]);
            String statusStr = parts[1];

            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                order.setStatus(OrderStatus.valueOf(statusStr));
                orderRepository.save(order);
                log.info("Updated order {} to status {}", orderId, statusStr);
            } else {
                log.warn("Order with id {} not found", orderId);
            }
        } else {
            log.error("Invalid message format: {}", message);
        }
    }

    @KafkaListener(topics = "payment-events", groupId = "order-group")
    public void consumePaymentEvent(String message) {
        log.info("Received payment event: {}", message);

        // Xử lý giống inventory-events
        String[] parts = message.split(":");
        if (parts.length == 2) {
            Long orderId = Long.parseLong(parts[0]);
            String statusStr = parts[1];

            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                order.setStatus(OrderStatus.valueOf(statusStr));
                orderRepository.save(order);
                log.info("Updated order {} to status {}", orderId, statusStr);
            } else {
                log.warn("Order with id {} not found", orderId);
            }
        } else {
            log.error("Invalid message format: {}", message);
        }
    }
}
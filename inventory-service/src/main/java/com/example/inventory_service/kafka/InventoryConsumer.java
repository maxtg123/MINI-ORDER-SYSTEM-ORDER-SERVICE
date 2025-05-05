package com.example.inventory_service.kafka;


import com.example.inventory_service.dto.OrderCreatedEvent;
import com.example.inventory_service.event.InventoryReservationFailedEvent;
import com.example.inventory_service.event.InventoryReservedEvent;
import com.example.inventory_service.model.Inventory;
import com.example.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryConsumer {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order-created", groupId = "inventory-group")
    public void consume(OrderCreatedEvent event) {
        log.info("Received order: {}", event);
        Inventory inventory = inventoryRepository.findByProductId(event.getProductId())
                .orElse(null);

        if (inventory != null && inventory.getQuantity() >= event.getQuantity()) {
            inventory.setQuantity(inventory.getQuantity() - event.getQuantity());
            inventoryRepository.save(inventory);
            kafkaTemplate.send("inventory-reserved", InventoryReservedEvent.builder()
                    .orderId(event.getOrderId())
                    .build());
            log.info("Inventory reserved");
        } else {
            kafkaTemplate.send("inventory-reservation-failed", InventoryReservationFailedEvent.builder()
                    .orderId(event.getOrderId())
                    .reason("Not enough stock")
                    .build());
            log.warn("Inventory reservation failed");
        }
    }
}

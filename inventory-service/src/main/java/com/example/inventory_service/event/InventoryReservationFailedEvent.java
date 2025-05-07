package com.example.inventory_service.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryReservationFailedEvent {
    private String orderId;
    private String reason;
}
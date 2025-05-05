//package com.example.inventory_service.Controller;
//
//import com.example.inventory_service.dto.OrderCreatedEvent;
//import com.example.inventory_service.kafka.InventoryConsumer;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/test-inventory")
//public class TestInventoryController {
//    private final InventoryConsumer consumer;
//
//    public TestInventoryController(InventoryConsumer consumer) {
//        this.consumer = consumer;
//    }
//
//    @PostMapping("/simulate")
//    public ResponseEntity<String> simulateOrderCreated(@RequestBody OrderCreatedEvent event) {
//        // gọi trực tiếp method listener (dù trong thực tế nó là KafkaListener)
//        consumer.consume(event);
//        return ResponseEntity.ok("Event manually consumed");
//    }
//}

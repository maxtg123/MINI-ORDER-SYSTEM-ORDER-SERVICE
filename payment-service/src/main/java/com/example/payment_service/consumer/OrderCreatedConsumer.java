package com.example.payment_service.consumer;


import com.example.payment_service.dto.OrderCreatedEvent;
import com.example.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCreatedConsumer {
    private final PaymentService paymentService;

    @KafkaListener(topics = "order-created-topic", groupId = "payment-group")
    public void consume(OrderCreatedEvent event) {
        paymentService.handlePayment(event);
    }
}

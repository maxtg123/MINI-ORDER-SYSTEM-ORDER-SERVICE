package com.example.payment_service.service;


import com.example.payment_service.dto.OrderCreatedEvent;
import com.example.payment_service.event.PaymentCompletedEvent;
import com.example.payment_service.event.PaymentFailedEvent;
import com.example.payment_service.model.Payment;
import com.example.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void handlePayment(OrderCreatedEvent event) {
        try {
            Payment payment = new Payment(event.getOrderId(), event.getAmount(), "COMPLETED");
            paymentRepository.save(payment);
            kafkaTemplate.send("payment-completed-topic", new PaymentCompletedEvent(event.getOrderId()));
        } catch (Exception e) {
            kafkaTemplate.send("payment-failed-topic", new PaymentFailedEvent(event.getOrderId(), "Payment processing failed"));
        }
    }
}

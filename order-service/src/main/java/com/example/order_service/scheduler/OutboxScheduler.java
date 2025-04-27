package com.example.order_service.scheduler;


import com.example.order_service.outbox.OutboxEvent;
import com.example.order_service.outbox.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxScheduler {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 5000)
    public void publishEvents() {
        List<OutboxEvent> events = outboxRepository.findByPublishedFalse();

        for (OutboxEvent event : events) {
            kafkaTemplate.send("order-created", event.getPayload());
            event.setPublished(true);
            outboxRepository.save(event);
            log.info("Published event to Kafka: {}", event.getPayload());
        }
    }
}

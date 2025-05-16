package com.example.MatchingService.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RideStartEventProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "ride-start";

    public void sendRideStartEvent(String driverId) {
        kafkaTemplate.send(TOPIC, driverId);
        System.out.println("send to ride-start : " + driverId);
    }
}

package com.example.MatchingService.producer;

import com.example.MatchingService.model.MatchedRide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RideMatchEventProducer {
    private final KafkaTemplate<String, MatchedRide> matchedRideKafkaTemplate;

    @Autowired
    public RideMatchEventProducer(KafkaTemplate<String, MatchedRide> matchedRideKafkaTemplate) {
        this.matchedRideKafkaTemplate = matchedRideKafkaTemplate;
    }

    private static final String TOPIC = "ride-match";

    public void sendRideMatchRequest(MatchedRide matchedRide) {
        matchedRideKafkaTemplate.send(TOPIC, matchedRide);
        System.out.println("sent to ride-match: " + matchedRide);
    }
}

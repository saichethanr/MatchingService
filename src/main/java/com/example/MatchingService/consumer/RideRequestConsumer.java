package com.example.MatchingService.consumer;

import com.example.MatchingService.dto.RiderRequestDTO;
import com.example.MatchingService.model.MatchedRide;
import com.example.MatchingService.producer.RideMatchEventProducer;
import com.example.MatchingService.service.MatchingService;
import com.example.MatchingService.util.RedisHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RideRequestConsumer {
    @Autowired
    private MatchingService matchingService;

    @Autowired
    private RideMatchEventProducer rideMatchEventProducer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "ride-requests", groupId = "matching-service-group")
    public void listenRideRequests(String message){
        try{
            RiderRequestDTO riderRequestDTO = objectMapper.readValue(message,RiderRequestDTO.class);
            System.out.println("deserialization done : " + riderRequestDTO.toString());
            String driverid = matchingService.matchDriver(riderRequestDTO);
            System.out.println("driver matched" + driverid);
            MatchedRide matchedRide = new MatchedRide(riderRequestDTO.getUserId(),driverid);
            rideMatchEventProducer.sendRideMatchRequest(matchedRide);
        }
        catch (Exception e){
           e.printStackTrace();
        }
    }
}

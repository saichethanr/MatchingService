package com.example.MatchingService.service;

import com.example.MatchingService.dto.RiderRequestDTO;
import com.example.MatchingService.model.MatchedRide;
import com.example.MatchingService.util.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MatchingService{
    @Autowired
    private RedisHelper redisHelper;


    public String matchDriver(RiderRequestDTO request) {
        String matchedDriverId = redisHelper.findNearestDriver(
                request.getCurrentLat(),
                request.getCurrentLong(),
                request.getRequired_seats()
        );

        if (matchedDriverId == null) {
            System.out.println("NO Driver matched try again");
        }
        System.out.println("driver matched : " + matchedDriverId);
        return matchedDriverId;
    }
}
package com.example.MatchingService.controller;

import com.example.MatchingService.service.RideStartTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matchingservice")
public class RideStartEventListner {
        @Autowired
        private RideStartTracker rideStartTracker;

        @PostMapping("/start-ride")
        public ResponseEntity<String> startRide(@RequestParam String driverId) {
            rideStartTracker.markRideStarted(driverId);
            return ResponseEntity.ok("Ride start registered for driver: " + driverId);
        }
}

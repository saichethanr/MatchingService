package com.example.MatchingService.service;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RideStartTracker {
    private final Set<String> startedRides = ConcurrentHashMap.newKeySet();

    public void markRideStarted(String driverId) {
        startedRides.add(driverId);
    }

    public boolean hasRideStarted(String driverId) {
        return startedRides.contains(driverId);
    }

    public void clear(String driverId) {
        startedRides.remove(driverId);
    }
}

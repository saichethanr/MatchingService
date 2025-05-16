package com.example.MatchingService.model;

public class MatchedRide {
    private String riderId;
    private String driverId;

    public MatchedRide() {
    }

    public MatchedRide(String riderId, String driverId) {
        this.riderId = riderId;
        this.driverId = driverId;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    @Override
    public String toString() {
        return "MatchedRide{" +
                "riderId='" + riderId + '\'' +
                ", driverId='" + driverId + '\'' +
                '}';
    }
}

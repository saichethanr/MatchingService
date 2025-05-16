package com.example.MatchingService.dto;

public class RiderRequestDTO {
    private String userId;
    private double currentLat;
    private double currentLong;
    private double destLat;
    private double destLong;
    private int required_seats;

    public int getRequired_seats() {
        return required_seats;
    }

    public void setRequired_seats(int required_seats) {
        this.required_seats = required_seats;
    }



    public RiderRequestDTO() {
    }

    public RiderRequestDTO(String userId, double currentLat, double currentLong, double destLat, double destLong) {
        this.userId = userId;
        this.currentLat = currentLat;
        this.currentLong = currentLong;
        this.destLat = destLat;
        this.destLong = destLong;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public double getCurrentLong() {
        return currentLong;
    }

    public void setCurrentLong(double currentLong) {
        this.currentLong = currentLong;
    }

    public double getDestLat() {
        return destLat;
    }

    public void setDestLat(double destLat) {
        this.destLat = destLat;
    }

    public double getDestLong() {
        return destLong;
    }

    public void setDestLong(double destLong) {
        this.destLong = destLong;
    }

    @Override
    public String toString() {
        return "RiderRequestDTO{" +
                "userId='" + userId + '\'' +
                ", currentLat=" + currentLat +
                ", currentLong=" + currentLong +
                ", destLat=" + destLat +
                ", destLong=" + destLong +
                '}';
    }
}

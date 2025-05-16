package com.example.MatchingService.util;

import com.example.MatchingService.producer.RideStartEventProducer;
import com.example.MatchingService.service.RideStartTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RedisHelper {
    private static final String DRIVER_GEO_KEY = "drivers:locations";

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private RideStartTracker rideStartTracker;

    @Autowired
    private RideStartEventProducer rideStartEventProducer;


    private static final String GEO_KEY = "drivers:locations";

    public String findNearestDriver(double latitude, double longitude, int requiredSeats) {
        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        Point riderLocation = new Point(longitude, latitude);

        double[] radii = {0.2, 0.5, 1, 2, 3, 5}; // in kilometers

        for (double radius : radii) {
            Circle area = new Circle(riderLocation, new Distance(radius, Metrics.KILOMETERS));
            GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                    geoOps.radius(GEO_KEY, area, RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().sortAscending());

            if (results != null && !results.getContent().isEmpty()) {
                for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results.getContent()) {
                    String driverId = result.getContent().getName();

                    int seats = getDriverSeats(driverId);
                        if (seats >= requiredSeats) {
                            decrementSeatOrRemoveDriver(driverId,requiredSeats);
                            return driverId;
                        }

                }
            }
        }

        return null;
    }

    public int getDriverSeats(String driverId) {
        String seats = (String) redisTemplate.opsForHash().get("driver:" + driverId, "seats");
        return seats != null ? Integer.parseInt(seats) : 0;
    }

    public void decrementSeatOrRemoveDriver(String driverId,int required_seats) {
        String hashKey = "driver:" + driverId;
        int seats = getDriverSeats(driverId);
        redisTemplate.opsForHash().put(hashKey, "seats", String.valueOf(seats - required_seats));
        System.out.println("updating the seats post match");
        int postupdateseats = getDriverSeats(driverId);

        if (postupdateseats < 1 || rideStartTracker.hasRideStarted(driverId)) {
            System.out.println("removing the driver from cache :" + driverId);
            redisTemplate.opsForGeo().remove("drivers:locations", driverId);
            redisTemplate.delete(hashKey);
            rideStartEventProducer.sendRideStartEvent(driverId);
        }
    }



    public Map<String, String> getDriverMetadata(String driverId) {
        Map<Object, Object> rawMap = redisTemplate.opsForHash().entries("driver:" + driverId);
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry : rawMap.entrySet()) {
            result.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return result;
    }

}

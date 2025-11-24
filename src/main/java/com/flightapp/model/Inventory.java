package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "inventories")
public class Inventory {
    @Id
    private String id;
    private String flightId;
    private Instant departureTime;
    private Instant arrivalTime;

    private int availableSeats;
    private Map<String, FareBucket> fareBuckets;
    private Map<String, String> seatMap;



    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FareBucket {
        private long price;
        private int available;
    }
}

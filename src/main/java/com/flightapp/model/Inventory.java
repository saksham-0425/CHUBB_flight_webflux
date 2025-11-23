package com.flightapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "inventories")
@CompoundIndexes({
    @CompoundIndex(name = "search_idx", def = "{'origin': 1, 'destination': 1, 'departureTime': 1}")
})
public class Inventory {

    @Id
    private String id;

    @NotBlank(message = "flightId required")
    private String flightId;

    @NotNull(message = "departureTime required")
    private Instant departureTime;

    @NotNull(message = "arrivalTime required")
    private Instant arrivalTime;

    private int availableSeats;

    private Map<String, FareBucket> fareBuckets;

    private Map<String, String> seatMap;
    @NotBlank(message = "Origin must not be blank")
    private String origin;

    @NotBlank(message = "Destination must not be blank")
    private String destination;

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

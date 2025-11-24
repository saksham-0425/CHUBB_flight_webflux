package com.flightapp.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.Map;

class InventoryTest {

    @Test
    void inventory_builder_shouldSetFields() {

        Inventory.FareBucket bucket = Inventory.FareBucket.builder()
                .price(3500)
                .available(5)
                .build();

        Inventory inventory = Inventory.builder()
                .id("INV1")
                .flightId("F1")
                .departureTime(Instant.parse("2025-01-01T10:00:00Z"))
                .arrivalTime(Instant.parse("2025-01-01T12:00:00Z"))
                .availableSeats(50)
                .fareBuckets(Map.of("economy", bucket))
                .seatMap(Map.of("A1", "AVAILABLE"))
                .build();

        assertThat(inventory.getFlightId()).isEqualTo("F1");
        assertThat(inventory.getFareBuckets().get("economy").getPrice()).isEqualTo(3500);
    }

    @Test
    void fareBucket_setters_shouldModifyValues() {
        Inventory.FareBucket bucket = new Inventory.FareBucket();
        bucket.setPrice(2000);
        bucket.setAvailable(10);

        assertThat(bucket.getPrice()).isEqualTo(2000);
        assertThat(bucket.getAvailable()).isEqualTo(10);
    }
}

package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Document(collection = "seats")
public class Seat {

    @Id
    private String id;

    @NotBlank(message = "inventoryId required")
    private String inventoryId;

    @NotBlank(message = "seatNumber required")
    private String seatNumber;

    private String seatClass;
    private String status;
}

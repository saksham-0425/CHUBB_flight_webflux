package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Document(collection = "payments")
public class Payment {

    @Id
    private String id;
    private String bookingId;
    private String method; 
    private long amount;
    private String status;
    private Instant paidAt;
}

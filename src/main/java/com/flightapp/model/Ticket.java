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
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String id;
    private String bookingId;
    private String pnr;
    private String ticketUrl;
    private Instant issuedAt;
}

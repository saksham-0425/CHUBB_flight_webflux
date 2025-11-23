package com.flightapp.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {

    private String pnr;
    private String ticketUrl;
    private Instant issuedAt;
    private String bookingId;
}

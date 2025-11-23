package com.flightapp.controller;

import com.flightapp.dto.response.TicketResponse;
import com.flightapp.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/{pnr}")
    public Mono<TicketResponse> getTicket(@PathVariable String pnr) {

        return ticketService.getTicketByPnr(pnr)
                .map(ticket -> TicketResponse.builder()
                        .pnr(ticket.getPnr())
                        .ticketUrl(ticket.getTicketUrl())
                        .issuedAt(ticket.getIssuedAt())
                        .bookingId(ticket.getBookingId())
                        .build());
    }
}

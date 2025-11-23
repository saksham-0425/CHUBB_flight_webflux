package com.flightapp.service;

import com.flightapp.model.Ticket;
import reactor.core.publisher.Mono;

public interface TicketService {
    Mono<Ticket> getTicketByPnr(String pnr);
}

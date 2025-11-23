package com.flightapp.service.impl;

import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.Ticket;
import com.flightapp.repository.TicketRepository;
import com.flightapp.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public Mono<Ticket> getTicketByPnr(final String pnr) {
        return ticketRepository.findByPnr(pnr)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Ticket not found for PNR: " + pnr)));
    }
}

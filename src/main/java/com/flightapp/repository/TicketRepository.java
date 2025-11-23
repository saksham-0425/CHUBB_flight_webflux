package com.flightapp.repository;

import com.flightapp.model.Ticket;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TicketRepository extends ReactiveMongoRepository<Ticket, String> {

    Mono<Ticket> findByPnr(String pnr);
}

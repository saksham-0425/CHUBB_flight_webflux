package com.flightapp.repository;

import com.flightapp.model.Inventory;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.Instant;

public interface InventoryRepository extends ReactiveMongoRepository<Inventory, String> {

	@Query("{ 'origin': ?0, 'destination': ?1, 'departureTime': ?2 }")
	Flux<Inventory> searchByRouteAndDate(String origin, String destination, Instant departureTime);

    

}

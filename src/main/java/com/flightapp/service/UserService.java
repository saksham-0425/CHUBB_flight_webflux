package com.flightapp.service;

import com.flightapp.model.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findByEmail(String email);
}

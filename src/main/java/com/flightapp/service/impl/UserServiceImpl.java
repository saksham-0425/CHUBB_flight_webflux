package com.flightapp.service.impl;

import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.User;
import com.flightapp.repository.UserRepository;
import com.flightapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<User> findByEmail(final String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("User not found: " + email)));
    }
}

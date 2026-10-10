package com.gymtracker.api.application.service;

import com.gymtracker.api.application.port.in.CreateUserUseCase;
import com.gymtracker.api.application.port.out.UserRepositoryPort;
import com.gymtracker.api.domain.exception.ResourceAlreadyExistsException;
import com.gymtracker.api.domain.model.User;

import java.time.Instant;

public class CreateUserService implements CreateUserUseCase {
    private final UserRepositoryPort userRepositoryPort;

    public CreateUserService(
            UserRepositoryPort userRepositoryPort
    ) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User create(String name, String email) {
        if (userRepositoryPort.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        User user = new User(null, name, email, Instant.now());

        return userRepositoryPort.save(user);
    }
}

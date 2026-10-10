package com.gymtracker.api.application.port.in;

import com.gymtracker.api.domain.model.User;

public interface CreateUserUseCase {
    User create(String name, String email);
}

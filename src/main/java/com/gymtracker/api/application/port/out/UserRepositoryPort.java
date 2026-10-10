package com.gymtracker.api.application.port.out;

import com.gymtracker.api.domain.model.User;

public interface UserRepositoryPort {
    boolean existsByEmail(String email);

    User save(User user);
}

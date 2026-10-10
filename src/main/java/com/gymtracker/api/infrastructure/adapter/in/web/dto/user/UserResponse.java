package com.gymtracker.api.infrastructure.adapter.in.web.dto.user;

import java.time.Instant;

public record UserResponse(
        Long id,
        String name,
        String email,
        Instant createdAt
) {
}

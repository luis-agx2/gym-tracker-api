package com.gymtracker.api.domain.model;

import java.time.Instant;

public class User {
    private final Long id;
    private final String name;
    private final String email;
    private final Instant createdAt;

    public User (
            Long id,
            String name,
            String email,
            Instant createdAt
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }

        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }

        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}

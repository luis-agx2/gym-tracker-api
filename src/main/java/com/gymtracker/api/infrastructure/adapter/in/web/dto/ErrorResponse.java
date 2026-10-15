package com.gymtracker.api.infrastructure.adapter.in.web.dto;

import java.time.Instant;

public record ErrorResponse (
        int codeError,
        String message,
        Instant timestamp
) {
}

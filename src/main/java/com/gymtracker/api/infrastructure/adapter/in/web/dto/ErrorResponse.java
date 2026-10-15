package com.gymtracker.api.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse (
        int codeError,
        String message,
        Instant timestamp,
        Map<String, String> fieldErrors
) {
}

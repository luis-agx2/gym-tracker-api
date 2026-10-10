package com.gymtracker.api.infrastructure.adapter.in.web.mapper;

import com.gymtracker.api.domain.model.User;
import com.gymtracker.api.infrastructure.adapter.in.web.dto.user.UserResponse;

public class UserWebMapper {
    public UserResponse toResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt()
        );
    }
}

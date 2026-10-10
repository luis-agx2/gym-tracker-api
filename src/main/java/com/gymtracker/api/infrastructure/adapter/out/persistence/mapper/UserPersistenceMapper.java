package com.gymtracker.api.infrastructure.adapter.out.persistence.mapper;

import com.gymtracker.api.domain.model.User;
import com.gymtracker.api.infrastructure.adapter.out.persistence.entity.UserJpaEntity;

public class UserPersistenceMapper {
    public UserJpaEntity toEntity(User user) {
        return new UserJpaEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    public User toDomain(UserJpaEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getCreatedAt()
        );
    }
}

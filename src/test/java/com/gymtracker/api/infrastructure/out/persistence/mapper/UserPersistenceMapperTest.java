package com.gymtracker.api.infrastructure.out.persistence.mapper;

import com.gymtracker.api.domain.model.User;
import com.gymtracker.api.infrastructure.adapter.out.persistence.entity.UserJpaEntity;
import com.gymtracker.api.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class UserPersistenceMapperTest {
    private final UserPersistenceMapper mapper = new UserPersistenceMapper();

    @Test
    void shouldMapDomainToEntity() {
        User user = new User(1L, "Carlos", "carlos@email.com", Instant.now());

        UserJpaEntity entity = mapper.toEntity(user);

        Assertions.assertEquals(user.getId(), entity.getId());
        Assertions.assertEquals(user.getName(), entity.getName());
        Assertions.assertEquals(user.getEmail(), entity.getEmail());
        Assertions.assertEquals(user.getCreatedAt(), entity.getCreatedAt());
    }

    @Test
    void shouldMapEntityToDomain() {
        UserJpaEntity entity = new UserJpaEntity(1L, "Carlos", "carlos@email.com", Instant.now());

        User user = mapper.toDomain(entity);

        Assertions.assertEquals(entity.getId(), user.getId());
        Assertions.assertEquals(entity.getName(), user.getName());
        Assertions.assertEquals(entity.getEmail(), user.getEmail());
        Assertions.assertEquals(entity.getCreatedAt(), user.getCreatedAt());
    }
}

package com.gymtracker.api.infrastructure.adapter.out.persistence;

import com.gymtracker.api.application.port.out.UserRepositoryPort;
import com.gymtracker.api.domain.model.User;
import com.gymtracker.api.infrastructure.adapter.out.persistence.entity.UserJpaEntity;
import com.gymtracker.api.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.gymtracker.api.infrastructure.adapter.out.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserPersistenceAdapter implements UserRepositoryPort {
    private final UserJpaRepository userJpaRepository;
    private final UserPersistenceMapper mapper;

    public UserPersistenceAdapter(
            UserJpaRepository userJpaRepository
    ) {
        this.userJpaRepository = userJpaRepository;
        this.mapper = new UserPersistenceMapper();
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = mapper.toEntity(user);

        UserJpaEntity savedEntity = userJpaRepository.save(entity);

        return mapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userJpaRepository.existsByEmail(email);
    }
}

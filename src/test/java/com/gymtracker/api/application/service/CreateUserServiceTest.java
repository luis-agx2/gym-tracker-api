package com.gymtracker.api.application.service;

import com.gymtracker.api.application.port.out.UserRepositoryPort;
import com.gymtracker.api.domain.exception.ResourceAlreadyExistsException;
import com.gymtracker.api.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CreateUserServiceTest {
    @Nested
    class CreateUser {
        @Test
        void shouldNotCreateUserWhenEmailAlreadyExists() {
            UserRepositoryPort repositoryPort = new FakeUserRepository(true);
            CreateUserService createUserService = new CreateUserService(repositoryPort);
            Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> createUserService.create("Carlos", "carlos@email.com"));
        }

        @Test
        void shouldCreateUserWhenEmailDoesNotExist() {
            UserRepositoryPort repositoryPort = new FakeUserRepository(false);
            CreateUserService createUserService = new CreateUserService(repositoryPort);

            User user = createUserService.create("Carlos", "carlos@email.com");

            Assertions.assertEquals("Carlos", user.getName());
            Assertions.assertEquals("carlos@email.com", user.getEmail());
        }

        private static class FakeUserRepository implements UserRepositoryPort {
            private final boolean emailExists;

            public FakeUserRepository(boolean emailExists) {
                this.emailExists = emailExists;
            }

            @Override
            public boolean existsByEmail(String email) {
                return emailExists;
            }

            @Override
            public User save(User user) {
                return user;
            }
        }
    }
}

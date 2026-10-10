package com.gymtracker.api.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class UserTest {
    @Nested
    @DisplayName("User creation test")
    class CreationTest {
        @Test
        void shouldNotCreateUserWithBlankName() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> new User(null, "", "test@example.com", Instant.now()));
        }

        @Test
        void shouldNotCreateUserWithBlankEmail() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> new User(null, "Juan Jose", null, Instant.now()));
        }

        @Test
        void shouldNotCreateUserWithoutCreatedAt() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> new User(1L, "Juan Jose", "juanjo@email.com", null));
        }

        @Test
        void shouldCreateUserSuccessfully() {
            User user = new User(1L, "Juan Jose", "juanjo@mail.com", Instant.now());

            Assertions.assertEquals(1L, user.getId());
            Assertions.assertEquals("Juan Jose", user.getName());
            Assertions.assertEquals("juanjo@mail.com", user.getEmail());
        }
    }
}

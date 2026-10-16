package com.gymtracker.api.infrastructure.adapter.in.web;

import com.gymtracker.api.application.port.in.CreateUserUseCase;
import com.gymtracker.api.domain.exception.ResourceAlreadyExistsException;
import com.gymtracker.api.domain.model.User;
import com.gymtracker.api.infrastructure.adapter.in.web.exception.GlobalExceptionHandler;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UserControllerTest {
    private MockMvc mockMvc;
    private FakeCreateUserUseCase fakeCreateUserUseCase;

    @BeforeEach
    void setUp() {
        fakeCreateUserUseCase = new FakeCreateUserUseCase();
        UserController userController = new UserController(fakeCreateUserUseCase);

        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldReturn201WhenUserIsCreated() throws Exception {
        User user = new User(15L, "Carlos", "carlos@email.com", Instant.now());

        fakeCreateUserUseCase.setUserToReturn(user);

        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Carlos",
                                    "email": "carlos@email.com"
                                }
                                """)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(15))
        .andExpect(jsonPath("$.name").value("Carlos"))
        .andExpect(jsonPath("$.email").value("carlos@email.com"));

        Assertions.assertEquals(1, fakeCreateUserUseCase.getCallCount());
        Assertions.assertEquals("Carlos", fakeCreateUserUseCase.getReceivedName());
        Assertions.assertEquals("carlos@email.com", fakeCreateUserUseCase.getReceivedEmail());
    }

    @Test
    void shouldReturn400WhenUserIsInvalid() throws Exception {
        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "",
                                    "email": "invalid-email"
                                }
                                """)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.codeError").value("400"))
        .andExpect(jsonPath("$.fieldErrors").isMap())
        .andExpect(jsonPath("$.fieldErrors.name").exists())
        .andExpect(jsonPath("$.fieldErrors.email").exists());

        Assertions.assertEquals(0, fakeCreateUserUseCase.getCallCount());
    }

    @Test
    void shouldReturn409WhenEmailIsAlreadyExists() throws Exception {
        fakeCreateUserUseCase.setExceptionToThrow(new ResourceAlreadyExistsException("message"));

        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Carlos",
                                    "email": "existing@email.com"
                                }
                                """)
        )
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.codeError").value(409))
        .andExpect(jsonPath("$.fieldErrors").doesNotExist());

        Assertions.assertEquals(1, fakeCreateUserUseCase.getCallCount());
        Assertions.assertEquals("Carlos", fakeCreateUserUseCase.getReceivedName());
        Assertions.assertEquals("existing@email.com", fakeCreateUserUseCase.getReceivedEmail());
    }

    private static class FakeCreateUserUseCase implements CreateUserUseCase {
        private User userToReturn;
        private RuntimeException exceptionToThrow;

        private String receivedName;
        private String receivedEmail;
        private int callCount;

        @Override
        public User create(String name, String email) {
            callCount++;
            receivedName = name;
            receivedEmail = email;

            if (exceptionToThrow != null) {
                throw exceptionToThrow;
            }

            return userToReturn;
        }

        public void setUserToReturn(User userToReturn) {
            this.userToReturn = userToReturn;
        }

        public void setExceptionToThrow(RuntimeException exception) {
            this.exceptionToThrow = exception;
        }

        public String getReceivedName() {
            return receivedName;
        }

        public String getReceivedEmail() {
            return receivedEmail;
        }

        public int getCallCount() {
            return callCount;
        }
    }
}

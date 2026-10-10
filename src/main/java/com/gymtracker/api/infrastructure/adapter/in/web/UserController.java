package com.gymtracker.api.infrastructure.adapter.in.web;

import com.gymtracker.api.application.port.in.CreateUserUseCase;
import com.gymtracker.api.domain.model.User;
import com.gymtracker.api.infrastructure.adapter.in.web.dto.user.CreateUserRequest;
import com.gymtracker.api.infrastructure.adapter.in.web.dto.user.UserResponse;
import com.gymtracker.api.infrastructure.adapter.in.web.mapper.UserWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final UserWebMapper mapper;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.mapper = new UserWebMapper();
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        User user = createUserUseCase.create(request.name(), request.email());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toResponse(user));
    }
}

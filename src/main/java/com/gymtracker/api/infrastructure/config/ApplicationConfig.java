package com.gymtracker.api.infrastructure.config;

import com.gymtracker.api.application.port.in.CreateUserUseCase;
import com.gymtracker.api.application.port.out.UserRepositoryPort;
import com.gymtracker.api.application.service.CreateUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public CreateUserUseCase createUserUseCase(UserRepositoryPort userRepositoryPort) {
        return new CreateUserService(userRepositoryPort);
    }
}

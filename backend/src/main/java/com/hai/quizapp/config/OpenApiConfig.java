package com.hai.quizapp.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * OpenAPI (Swagger) configuration.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Quiz Application API",
                version = "1.0",
                description = "Quiz Management System API - Question and Answer Platform",
                contact = @Contact(
                        name = "Quiz App Support",
                        email = "support@quizapp.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
            @Server(url = "http://localhost:8080", description = "Development Server")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Enter your JWT token. Get it from /api/auth/login endpoint."
)
public class OpenApiConfig {
}

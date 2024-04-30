package ru.zyablov.t1.authjwt.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@OpenAPIDefinition(
        info = @Info(
                title = "AuthJwtService",
                description = "Тестовый проект для отработки SpringSecurity с JWT токеном", version = "1.0.0",
                contact = @Contact(
                        name = "Zyablov Anton",
                        email = "ahton005@yandex.ru"
                )
        )
)
@SecurityScheme(
        name = "JWT",
        type = HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}

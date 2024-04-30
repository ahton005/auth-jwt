package ru.zyablov.t1.authjwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static ru.zyablov.t1.authjwt.utils.StringUtils.APPLICATION_JSON;

/**
 * Контроллер для тестирования доступа
 */
@RequiredArgsConstructor
@RestController
@Tag(name = "Тестовый контроллер", description = "Позволяет проверить доступ к методам")
public class TestController {
    /**
     * Получить информацию для админов.
     *
     * @return String
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
            @ApiResponse(responseCode = "500", description = "Ошибка на сервере")
    })
    @Operation(summary = "Доступ только для админов")
    @SecurityRequirement(name = "JWT")
    @GetMapping(value = "/admin", produces = APPLICATION_JSON)
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("Hello Admin!!!");
    }

    /**
     * Получить информацию для пользователей.
     *
     * @return String
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
            @ApiResponse(responseCode = "500", description = "Ошибка на сервере")
    })
    @Operation(summary = "Доступ для админов и пользователей")
    @SecurityRequirement(name = "JWT")
    @GetMapping(value = "/user", produces = APPLICATION_JSON)
    public ResponseEntity<String> helloUser() {
        return ResponseEntity.ok("Hello User!!!");
    }


    /**
     * Получить информацию не требующую аутентификации.
     *
     * @return String
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
            @ApiResponse(responseCode = "500", description = "Ошибка на сервере")
    })
    @Operation(summary = "Доступ для всех")
    @GetMapping(value = "/anon", produces = APPLICATION_JSON)
    public ResponseEntity<String> helloAnon() {
        return ResponseEntity.ok("Hello Anon!!!");
    }
}

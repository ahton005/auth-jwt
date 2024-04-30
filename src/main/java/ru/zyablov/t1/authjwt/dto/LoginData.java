package ru.zyablov.t1.authjwt.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос токена
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос токена")
public class LoginData {
    @Schema(description = "Имя пользователя")
    private String username;
    @Schema(description = "Пароль")
    private String password;
}

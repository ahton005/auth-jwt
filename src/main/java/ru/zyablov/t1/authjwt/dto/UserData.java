package ru.zyablov.t1.authjwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос на регистрацию
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Запрос на регистрацию")
public class UserData {
    @Schema(description = "Имя пользователя")
    private String username;
    @Schema(description = "Почта")
    private String email;
    @Schema(description = "Пароль")
    private String password;
}

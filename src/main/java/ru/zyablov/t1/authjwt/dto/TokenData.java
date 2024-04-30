package ru.zyablov.t1.authjwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Токен доступа
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с токеном")
public class TokenData {
    @Schema(description = "Токен")
    private String token;
}

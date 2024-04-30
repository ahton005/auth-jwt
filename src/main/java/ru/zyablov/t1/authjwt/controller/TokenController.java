package ru.zyablov.t1.authjwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zyablov.t1.authjwt.dto.LoginData;
import ru.zyablov.t1.authjwt.dto.TokenData;
import ru.zyablov.t1.authjwt.dto.UserData;
import ru.zyablov.t1.authjwt.exception.AuthorityNotFoundException;
import ru.zyablov.t1.authjwt.exception.UserExistException;
import ru.zyablov.t1.authjwt.service.AppUserDetailsService;
import ru.zyablov.t1.authjwt.service.TokenService;

import static ru.zyablov.t1.authjwt.utils.StringUtils.APPLICATION_JSON;

/**
 * Контроллер для тестирования доступа
 */
@RestController
@RequestMapping("api/public")
@RequiredArgsConstructor
@Tag(name = "Контроллер для получения токена и регистрации пользователя")
public class TokenController {

    private final AppUserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;


    /**
     * Получить токен доступа на основе логина/пароля.
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
            @ApiResponse(responseCode = "500", description = "Ошибка на сервере")
    })
    @Operation(summary = "Залогиниться и получить токен")
    @PostMapping(value = "/login", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
    public ResponseEntity<?> getAccessToken(@RequestBody LoginData loginData) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));
        var user = userDetailsService.loadUserByUsername(loginData.getUsername());
        var token = tokenService.generateToken(user);
        return ResponseEntity.ok().body(new TokenData(token));
    }

    /**
     * Зарегистрировать нового пользователя.
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
            @ApiResponse(responseCode = "500", description = "Ошибка на сервере")
    })
    @Operation(summary = "Зарегистрировать нового пользователя")
    @PostMapping(value = "/registration", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
    public ResponseEntity<Void> createUser(@RequestBody UserData userData) throws AuthorityNotFoundException, UserExistException {
        userDetailsService.create(userData);
        return ResponseEntity.noContent().build();
    }
}

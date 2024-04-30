package ru.zyablov.t1.authjwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class TokenServiceImpl implements TokenService {
    private static final String AUTHORITIES = "authorities";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.ttl:1m}")
    private Duration tokenTtl;

    /**
     * Сгенерировать токен
     *
     * @param userDetails Данные о пользователе
     * @return Токен авторизации
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenTtl.toMillis());
        return Jwts.builder().subject(userDetails.getUsername())
                .claim(AUTHORITIES, userDetails.getAuthorities().stream().map(String::valueOf).toList())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * Получить имя пользователя из токена
     *
     * @param token Токен доступа
     * @return Имя пользователя
     */
    @Override
    public String getUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Получить список привилегий пользователя
     *
     * @param token Токен доступа
     * @return Список привилегий
     */
    @Override
    public List<String> getAuthorities(String token) {
        return getClaimsFromToken(token).get(AUTHORITIES, List.class);
    }

    /**
     * Сгенерировать секретный ключ
     *
     * @return Секретный ключ для подписи токена
     */
    private SecretKeySpec getSecretKey() {
        return new SecretKeySpec(secret.getBytes(UTF_8), HS256.getJcaName());
    }

    /**
     * Получить список "клеймов" из токена
     *
     * @param token Токен доступа
     * @return Список "клеймов"
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

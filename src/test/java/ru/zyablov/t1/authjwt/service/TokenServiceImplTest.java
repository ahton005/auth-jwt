package ru.zyablov.t1.authjwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(tokenService, "secret", "testT165ds4f5dsf45dsf4d56s4f5dsf46sf4d5");
        ReflectionTestUtils.setField(tokenService, "tokenTtl", Duration.ofMinutes(1));
    }

    @Test
    public void testGenerateToken() {
        // given
        doReturn("testUser").when(userDetails).getUsername();
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_USER"))).when(userDetails).getAuthorities();

        //when
        String token = tokenService.generateToken(userDetails);

        //then
        Claims claims = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
        assertEquals("testUser", claims.getSubject());
        List<String> authorities = claims.get("authorities", List.class);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.get(0));
        verify(userDetails).getUsername();
        verify(userDetails).getAuthorities();
        verifyNoMoreInteractions(userDetails);
    }

    private SecretKeySpec getSecretKey() {
        return new SecretKeySpec("testT165ds4f5dsf45dsf4d56s4f5dsf46sf4d5".getBytes(UTF_8), HS256.getJcaName());
    }

    @Test
    public void testGetUsername() {
        // given
        doReturn("TEST").when(userDetails).getUsername();
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_USER"))).when(userDetails).getAuthorities();

        // when
        String token = tokenService.generateToken(userDetails);
        String username = tokenService.getUsername(token);

        // then
        assertEquals("TEST", username);
        verify(userDetails).getUsername();
        verify(userDetails).getAuthorities();
        verifyNoMoreInteractions(userDetails);
    }

    @Test
    public void testGetAuthorities() {
        // given
        doReturn("TEST").when(userDetails).getUsername();
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_USER"))).when(userDetails).getAuthorities();

        //when
        String token = tokenService.generateToken(userDetails);
        List<String> authorities = tokenService.getAuthorities(token);

        //then
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.get(0));
        verify(userDetails).getUsername();
        verify(userDetails).getAuthorities();
        verifyNoMoreInteractions(userDetails);
    }
}
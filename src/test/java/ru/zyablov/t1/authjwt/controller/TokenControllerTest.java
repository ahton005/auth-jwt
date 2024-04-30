package ru.zyablov.t1.authjwt.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import ru.zyablov.t1.authjwt.dto.LoginData;
import ru.zyablov.t1.authjwt.dto.TokenData;
import ru.zyablov.t1.authjwt.dto.UserData;
import ru.zyablov.t1.authjwt.exception.AuthorityNotFoundException;
import ru.zyablov.t1.authjwt.exception.UserExistException;
import ru.zyablov.t1.authjwt.service.AppUserDetailsService;
import ru.zyablov.t1.authjwt.service.TokenService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class TokenControllerTest {
    @Mock
    AppUserDetailsService userDetailsService;
    @Mock
    TokenService tokenService;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    TokenController controller;


    @Test
    void getAccessToken_ValidData_ReturnToken_200OK() {
        // given
        LoginData loginData = new LoginData("username", "password");
        doReturn(mock(Authentication.class)).when(authenticationManager).authenticate(any());
        doReturn(mock(UserDetails.class)).when(userDetailsService).loadUserByUsername(anyString());
        doReturn("token").when(tokenService).generateToken(any());

        // when
        var response = controller.getAccessToken(loginData);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof TokenData);
        assertEquals("token", ((TokenData) response.getBody()).getToken());
        verify(authenticationManager).authenticate(any());
        verify(userDetailsService).loadUserByUsername(any());
        verify(tokenService).generateToken(any());
        verifyNoMoreInteractions(authenticationManager);
        verifyNoMoreInteractions(userDetailsService);
        verifyNoMoreInteractions(tokenService);
    }

    @Test
    void createUser() throws AuthorityNotFoundException, UserExistException {
        // given
        var userData = new UserData("test", "test", "test");
        doNothing().when(userDetailsService).create(userData);
        //when
        var res = controller.createUser(userData);

        //then
        assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());
        assertNull(res.getBody());
        verify(userDetailsService).create(userData);
        verifyNoMoreInteractions(userDetailsService);
    }
}
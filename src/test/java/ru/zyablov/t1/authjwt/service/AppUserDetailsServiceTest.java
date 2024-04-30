package ru.zyablov.t1.authjwt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.zyablov.t1.authjwt.dto.UserData;
import ru.zyablov.t1.authjwt.entity.Authority;
import ru.zyablov.t1.authjwt.entity.User;
import ru.zyablov.t1.authjwt.exception.AuthorityNotFoundException;
import ru.zyablov.t1.authjwt.exception.UserExistException;
import ru.zyablov.t1.authjwt.repository.AuthorityRepository;
import ru.zyablov.t1.authjwt.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AppUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AppUserDetailsService appUserDetailsService;

    private static final String ROLE_USER = "ROLE_USER";

    @Test
    public void testLoadUserByUsername_UserFound_ReturnUserDetails() {
        // given
        String username = "testUser";
        User user = User.builder()
                .username(username)
                .password("pass")
                .email("mail")
                .authorities(List.of(Authority.builder().name("ROLE_USER").build()))
                .build();
        doReturn(Optional.of(user)).when(userRepository).findByUsernameIgnoreCase(username);

        // when
        var res = appUserDetailsService.loadUserByUsername(username);

        // then
        assertEquals(username, res.getUsername());
        assertEquals(user.getPassword(), res.getPassword());
        assertEquals(user.getAuthorities().stream().map(Authority::getName).map(SimpleGrantedAuthority::new).toList(), res.getAuthorities());
        assertEquals(1, res.getAuthorities().size());
        verify(userRepository).findByUsernameIgnoreCase(username);
        verifyNoMoreInteractions(userRepository);
    }

    @Test()
    public void testLoadUserByUsername_ReturnExceptionUserNotFound() {
        // given
        String username = "nonExistingUser";
        String msg = "Пользователь %s не найден".formatted(username);
        doReturn(Optional.empty()).when(userRepository).findByUsernameIgnoreCase(username);

        // when
        var res = assertThrows(UsernameNotFoundException.class, () -> appUserDetailsService.loadUserByUsername(username));

        // then
        assertEquals(msg, res.getMessage());
        verify(userRepository).findByUsernameIgnoreCase(username);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testCreate_UserNotExist_ReturnVoid() throws UserExistException, AuthorityNotFoundException {
        //given
        UserData userData = new UserData("newUser", "password", "newuser@example.com");
        doReturn(false).when(userRepository).existsByUsernameIgnoreCase(userData.getUsername());
        doReturn(false).when(userRepository).existsByEmailIgnoreCase(userData.getEmail());
        Authority authority = Authority.builder().name(ROLE_USER).build();
        doReturn(Optional.of(authority)).when(authorityRepository).findByNameIgnoreCase(ROLE_USER);

        // when
        appUserDetailsService.create(userData);

        // then
        verify(userRepository).save(any(User.class));
        verify(userRepository).existsByEmailIgnoreCase(userData.getEmail());
        verify(userRepository).existsByUsernameIgnoreCase(userData.getUsername());
        verifyNoMoreInteractions(userRepository);
    }

    @Test()
    public void testCreate_UsernameExists_ReturnUserExistException() {
        // given
        String msg = "Пользователь с таким именем или email уже существует";
        UserData userData = new UserData("existingUser", "password", "existinguser@example.com");
        doReturn(true).when(userRepository).existsByUsernameIgnoreCase(userData.getUsername());

        // when
        var res = assertThrows(UserExistException.class, () -> appUserDetailsService.create(userData));

        // then
        assertEquals(msg, res.getMessage());
        verify(userRepository).existsByUsernameIgnoreCase(userData.getUsername());
        verifyNoMoreInteractions(userRepository);
    }

    @Test()
    public void testCreate_EmailExists_ReturnUserExistException() {
        // given
        String msg = "Пользователь с таким именем или email уже существует";
        UserData userData = new UserData("existingUser", "password", "existinguser@example.com");
        doReturn(false).when(userRepository).existsByUsernameIgnoreCase(userData.getUsername());
        doReturn(true).when(userRepository).existsByEmailIgnoreCase(userData.getEmail());

        // when
        var res = assertThrows(UserExistException.class, () -> appUserDetailsService.create(userData));

        // then
        assertEquals(msg, res.getMessage());
        verify(userRepository).existsByUsernameIgnoreCase(userData.getUsername());
        verifyNoMoreInteractions(userRepository);
    }

    @Test()
    public void testCreate_AuthorityNotFound() {
        // given
        String msg = "Роль %s не найдена в БД".formatted(ROLE_USER);
        UserData userData = new UserData("newUser", "password", "newuser@example.com");
        doReturn(false).when(userRepository).existsByUsernameIgnoreCase(userData.getUsername());
        doReturn(false).when(userRepository).existsByEmailIgnoreCase(userData.getEmail());
        doReturn(Optional.empty()).when(authorityRepository).findByNameIgnoreCase(ROLE_USER);

        // when
        var res = assertThrows(AuthorityNotFoundException.class, () -> appUserDetailsService.create(userData));

        // then
        assertEquals(msg, res.getMessage());
        verify(userRepository).existsByUsernameIgnoreCase(userData.getUsername());
        verify(userRepository).existsByEmailIgnoreCase(userData.getEmail());
        verifyNoMoreInteractions(userRepository);
    }
}
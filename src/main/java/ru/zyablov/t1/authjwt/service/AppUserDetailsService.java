package ru.zyablov.t1.authjwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zyablov.t1.authjwt.dto.UserData;
import ru.zyablov.t1.authjwt.entity.AppUserDetails;
import ru.zyablov.t1.authjwt.entity.User;
import ru.zyablov.t1.authjwt.exception.AuthorityNotFoundException;
import ru.zyablov.t1.authjwt.exception.UserExistException;
import ru.zyablov.t1.authjwt.repository.AuthorityRepository;
import ru.zyablov.t1.authjwt.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserDetailsService implements UserDetailsService {
    public static final String ROLE_USER = "ROLE_USER";
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Найти пользователя по логину(имени пользователя)
     *
     * @param username Имя пользователя.
     * @return Данные о пользователе
     * @throws UsernameNotFoundException Ошибка если пользователь не найден
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username)
                .map(AppUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь %s не найден".formatted(username)));
    }

    /**
     * Создать нового пользователя в БД
     *
     * @param user Данные пользователя
     * @throws UserExistException         Пользователь с такими данными уже существует в БД
     * @throws AuthorityNotFoundException Не найдена запись со стартовой ролью в БД
     */
    @Transactional
    public void create(UserData user) throws UserExistException, AuthorityNotFoundException {
        if (userRepository.existsByUsernameIgnoreCase(user.getUsername()) || userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new UserExistException("Пользователь с таким именем или email уже существует");
        }
        var authority = authorityRepository.findByNameIgnoreCase(ROLE_USER)
                .orElseThrow(() -> new AuthorityNotFoundException("Роль %s не найдена в БД".formatted(ROLE_USER)));
        userRepository.save(User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .authorities(List.of(authority))
                .build());
    }
}

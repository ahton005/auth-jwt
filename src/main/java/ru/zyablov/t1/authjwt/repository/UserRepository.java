package ru.zyablov.t1.authjwt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.zyablov.t1.authjwt.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsernameIgnoreCase(String name);

    boolean existsByUsernameIgnoreCase(String name);

    boolean existsByEmailIgnoreCase(String email);
}

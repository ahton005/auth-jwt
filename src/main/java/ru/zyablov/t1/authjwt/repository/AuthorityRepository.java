package ru.zyablov.t1.authjwt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.zyablov.t1.authjwt.entity.Authority;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Optional<Authority> findByNameIgnoreCase(String name);
}

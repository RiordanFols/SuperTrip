package ru.chernov.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.diplom.domain.entity.User;

/**
 * @author Pavel Chernov
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
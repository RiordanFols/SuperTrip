package ru.chernov.supertrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.supertrip.domain.entity.User;

/**
 * @author Pavel Chernov
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
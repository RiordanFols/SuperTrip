package ru.chernov.diplom.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.chernov.diplom.domain.Role;
import ru.chernov.diplom.domain.entity.User;
import ru.chernov.diplom.repository.UserRepository;

import java.util.Collections;

/**
 * @author Pavel Chernov
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return user != null ? user : new User();
    }

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void registration(String username, String name, String surname, String middleName,
                             int passportId, int passportSeries, String password) {
        User user = new User(name, surname, middleName, passportId, passportSeries);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        save(user);
    }

    public void managerRegistration(String username, String name, String surname, String middleName, String password) {
        User user = new User(name, surname, middleName, 0, 0);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(Role.MANAGER));
        user.setActive(true);
        save(user);
    }

    public void activate(long userId) {
        User user = findById(userId);
        user.setActive(true);
        save(user);
    }

    public void block(long userId) {
        User user = findById(userId);
        user.setActive(false);
        save(user);
    }
}

package ru.chernov.diplom.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.chernov.diplom.domain.entity.User;
import ru.chernov.diplom.page.Error;
import ru.chernov.diplom.service.UserService;

/**
 * @author Pavel Chernov
 */
@Component
public class FormChecker {

    private final UserService userService;

    @Autowired
    public FormChecker(UserService userService) {
        this.userService = userService;
    }

    public Error checkRegistrationData(String username, String password, String passwordConfirm) {

        // if username is taken
        if (userService.loadUserByUsername(username) != null)
            return Error.USERNAME_IS_TAKEN;

        // if password is too short (at least 6 symbols)
        if (password.length() < 6)
            return Error.TOO_SHORT_PASSWORD;

        // if password and passwordConfirm are different
        if (!password.equals(passwordConfirm))
            return Error.DIFFERENT_PASSWORDS;

        return null;
    }
}

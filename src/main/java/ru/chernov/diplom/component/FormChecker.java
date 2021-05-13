package ru.chernov.diplom.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.chernov.diplom.page.error.AuthError;
import ru.chernov.diplom.page.error.MainError;
import ru.chernov.diplom.service.NodeService;
import ru.chernov.diplom.service.UserService;

import java.time.LocalDateTime;

/**
 * @author Pavel Chernov
 */
@Component
public class FormChecker {

    private final NodeService nodeService;
    private final UserService userService;

    @Autowired
    public FormChecker(NodeService nodeService, UserService userService) {
        this.nodeService = nodeService;
        this.userService = userService;
    }

    public String checkRegistrationData(String username, String password, String passwordConfirm) {

        // if username is taken
        if (userService.loadUserByUsername(username) != null)
            return AuthError.USERNAME_IS_TAKEN.toString();

        // if password is too short (at least 6 symbols)
        if (password.length() < 6)
            return AuthError.TOO_SHORT_PASSWORD.toString();

        // if password and passwordConfirm are different
        if (!password.equals(passwordConfirm))
            return AuthError.DIFFERENT_PASSWORDS.toString();

        return null;
    }

    public String checkRouteSearchForm(String from, String to, LocalDateTime fromTime, LocalDateTime toTime,
                                      boolean busAllowed, boolean trainAllowed, boolean planeAllowed) {

        // can't find city
        if (nodeService.findByName(from) == null)
            return MainError.WRONG_NODE_NAME.toString() + from;
        if (nodeService.findByName(to) == null)
            return MainError.WRONG_NODE_NAME.toString() + to;

        // the same departure and arrival cities
        if (from.equals(to))
            return MainError.SAME_NODES.toString();

        // arrival time is before departure time
        if (toTime.isBefore(fromTime) || toTime.isEqual(fromTime))
            return MainError.NEGATIVE_TIME_DIF.toString();

        // if no transport selected
        if (!busAllowed && !trainAllowed && !planeAllowed)
            return MainError.NO_TRANSPORT_SELECTED.toString();

        return null;
    }
}

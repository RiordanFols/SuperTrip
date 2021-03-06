package ru.chernov.supertrip.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.chernov.supertrip.domain.entity.User;
import ru.chernov.supertrip.page.error.AuthError;
import ru.chernov.supertrip.page.error.MainError;
import ru.chernov.supertrip.page.error.UserDataError;
import ru.chernov.supertrip.service.NodeService;
import ru.chernov.supertrip.service.UserService;

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

    public String checkRegistrationData(String username, String password, String passwordConfirm,
                                        String name, String surname, String middleName,
                                        String passportId, String passportSeries) {

        // if username is taken
        if (userService.loadUserByUsername(username) != null &&
                !userService.loadUserByUsername(username).equals(new User()))
            return AuthError.USERNAME_IS_TAKEN.toString();

        // if password is too short (at least 6 symbols)
        if (password.length() < 6)
            return AuthError.TOO_SHORT_PASSWORD.toString();

        // if password and passwordConfirm are different
        if (!password.equals(passwordConfirm))
            return AuthError.DIFF_PASSWORDS.toString();

        return checkUserData(name, surname, middleName, passportId, passportSeries);
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
            return MainError.NEGATIVE_TIME_DIFF.toString();

        // if no transport selected
        if (!busAllowed && !trainAllowed && !planeAllowed)
            return MainError.NO_TRANSPORT_SELECTED.toString();

        return null;
    }

    public String checkUserData(String name, String surname, String middleName,
                                String passportId, String passportSeries) {

        // if names contains numbers
        if (name.matches(".*\\d.*"))
            return UserDataError.NAME_HAS_DIGITS.toString();
        if (surname.matches(".*\\d.*"))
            return UserDataError.SURNAME_HAS_DIGITS.toString();
        if (middleName.matches(".*\\d.*"))
            return UserDataError.MIDDLE_NAME_HAS_DIGITS.toString();

        // not numeric passport id or series
        try {
            Integer.parseInt(passportId);
            Integer.parseInt(passportSeries);
        } catch (NumberFormatException e) {
            return UserDataError.NOT_NUMERIC_PASSPORT.toString();
        }

        // wrong length of passport data
        if (passportId.length() != 6)
            return UserDataError.WRONG_LENGTH_PASSPORT_ID.toString();
        if (passportSeries.length() != 4)
            return UserDataError.WRONG_LENGTH_PASSPORT_SERIES.toString();

        return null;
    }
}

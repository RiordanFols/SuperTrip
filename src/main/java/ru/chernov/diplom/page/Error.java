package ru.chernov.diplom.page;

import lombok.ToString;

/**
 * @author Pavel Chernov
 */
@ToString(of = "description", includeFieldNames = false)
public enum Error {
    USERNAME_IS_TAKEN("User with this username already exists"),
    DIFFERENT_PASSWORDS("Passwords are not the same"),
    TOO_SHORT_PASSWORD("Password is too short (need at least 6 symbols)");

    private final String description;

    Error(String description) {
        this.description = description;
    }
}

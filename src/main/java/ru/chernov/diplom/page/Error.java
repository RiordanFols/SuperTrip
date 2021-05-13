package ru.chernov.diplom.page;

/**
 * @author Pavel Chernov
 */
public enum Error {
    // registration
    USERNAME_IS_TAKEN("User with this username already exists"),
    DIFFERENT_PASSWORDS("Passwords are not the same"),
    TOO_SHORT_PASSWORD("Password is too short (need at least 6 symbols)"),

    // login
    WRONG_CREDENTIALS("Wrong username or password"),
    ;

    private final String description;

    Error(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

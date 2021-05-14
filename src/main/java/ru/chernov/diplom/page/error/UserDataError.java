package ru.chernov.diplom.page.error;

import lombok.AllArgsConstructor;

/**
 * @author Pavel Chernov
 */
@AllArgsConstructor
public enum UserDataError {
    NOT_NUMERIC_PASSPORT("Passport must contains numbers only"),
    WRONG_LENGTH_PASSPORT_ID("Passport id must have 4 digits"),
    WRONG_LENGTH_PASSPORT_SERIES("Passport series must have 6 digits"),
    NAME_HAS_DIGITS("Name must not contain numbers"),
    SURNAME_HAS_DIGITS("Surname must not contain numbers"),
    MIDDLE_NAME_HAS_DIGITS("MiddleName must not contain numbers");

    private final String description;

    @Override
    public String toString() {
        return this.description;
    }
}

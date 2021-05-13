package ru.chernov.diplom.page.error;

import lombok.AllArgsConstructor;

/**
 * @author Pavel Chernov
 */
@AllArgsConstructor
public enum UserDataError {
    NOT_NUMERIC_PASSPORT("Passport must contains numbers only"),
    WRONG_LENGTH_PASSPORT_ID("Passport id must have 4 digits"),
    WRONG_LENGTH_PASSPORT_SERIES("Passport series must have 6 digits");

    private final String description;

    @Override
    public String toString() {
        return this.description;
    }
}

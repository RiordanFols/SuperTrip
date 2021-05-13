package ru.chernov.diplom.page;

/**
 * @author Pavel Chernov
 */
public enum Notification {
    REGISTRATION_SUCCESSFUL("Registration is successful"),
    ;

    private final String description;

    Notification(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

package ru.chernov.supertrip.page.notification;

/**
 * @author Pavel Chernov
 */
public enum AuthNotification {
    REGISTRATION_SUCCESSFUL("Registration is successful"),
    ;

    private final String description;

    AuthNotification(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

package ru.chernov.diplom.page;

import lombok.ToString;

/**
 * @author Pavel Chernov
 */
@ToString(of = "description", includeFieldNames = false)
public enum Notification {
    ;

    private final String description;

    Notification(String description) {
        this.description = description;
    }
}

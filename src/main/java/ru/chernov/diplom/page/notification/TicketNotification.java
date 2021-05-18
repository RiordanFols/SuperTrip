package ru.chernov.diplom.page.notification;

/**
 * @author Pavel Chernov
 */
public enum TicketNotification {
    PAYMENT_SUCCESSFUL("Payment successful. Thank you for using SUPER TRIP!");

    private final String description;

    TicketNotification(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

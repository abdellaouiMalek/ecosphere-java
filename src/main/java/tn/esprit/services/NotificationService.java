package tn.esprit.services;

import org.controlsfx.control.Notifications;

public class NotificationService {
    public void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .show();
    }
}

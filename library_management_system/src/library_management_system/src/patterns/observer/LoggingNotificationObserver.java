package library_management_system.src.patterns.observer;

import java.util.logging.Logger;

public class LoggingNotificationObserver implements NotificationObserver {
    private static final Logger logger = Logger.getLogger(LoggingNotificationObserver.class.getName());

    @Override
    public void onNotification(String patronId, String message) {
        logger.info("Notification logged for patron " + patronId + ": " + message);
    }
}
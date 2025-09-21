package library_management_system.src.patterns.observer;

import java.util.*;
import java.util.logging.Logger;

public class EmailNotificationService implements NotificationService {
    private static final Logger logger = Logger.getLogger(EmailNotificationService.class.getName());
    private final List<NotificationObserver> observers = new ArrayList<>();

    @Override
    public void sendNotification(String patronId, String message) {
        logger.info("Sending email notification to patron " + patronId + ": " + message);
        notifyObservers(patronId, message);
    }

    @Override
    public void addObserver(NotificationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(NotificationObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String patronId, String message) {
        for (NotificationObserver observer : observers) {
            observer.onNotification(patronId, message);
        }
    }
}

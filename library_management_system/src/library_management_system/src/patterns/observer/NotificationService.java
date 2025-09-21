package library_management_system.src.patterns.observer;

public interface NotificationService {
    void sendNotification(String patronId, String message);
    void addObserver(NotificationObserver observer);
    void removeObserver(NotificationObserver observer);
}

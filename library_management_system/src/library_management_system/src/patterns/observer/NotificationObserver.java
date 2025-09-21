package library_management_system.src.patterns.observer;

public interface NotificationObserver {
    void onNotification(String patronId, String message);
}
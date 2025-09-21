package library_management_system.src.domain;

import java.time.LocalDate;

public class Reservation {
    private String reservationId;
    private String patronId;
    private String isbn;
    private LocalDate reservationDate;
    private boolean isActive;

    public Reservation(String reservationId, String patronId, String isbn) {
        this.reservationId = reservationId;
        this.patronId = patronId;
        this.isbn = isbn;
        this.reservationDate = LocalDate.now();
        this.isActive = true;
    }

    // Getters and Setters
    public String getReservationId() { return reservationId; }
    public String getPatronId() { return patronId; }
    public String getIsbn() { return isbn; }
    public LocalDate getReservationDate() { return reservationDate; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }

    @Override
    public String toString() {
        return String.format("Reservation{id='%s', patron='%s', isbn='%s', date=%s, active=%s}", 
                           reservationId, patronId, isbn, reservationDate, isActive);
    }
}

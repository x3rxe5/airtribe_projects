package library_management_system.src.patterns.factory;

import java.util.UUID;

public class EntityFactory {
    
    public static Book createBook(String title, String author, int publicationYear, String genre) {
        String isbn = generateIsbn();
        return new Book(isbn, title, author, publicationYear, genre);
    }
    
    public static Patron createPatron(String name, String email, String phone) {
        String patronId = "PAT" + UUID.randomUUID().toString().substring(0, 8);
        return new Patron(patronId, name, email, phone);
    }
    
    public static Loan createLoan(String patronId, String isbn) {
        String loanId = "LOAN" + UUID.randomUUID().toString().substring(0, 8);
        return new Loan(loanId, patronId, isbn);
    }
    
    public static Reservation createReservation(String patronId, String isbn) {
        String reservationId = "RES" + UUID.randomUUID().toString().substring(0, 8);
        return new Reservation(reservationId, patronId, isbn);
    }
    
    private static String generateIsbn() {
        return "978" + String.format("%010d", (long)(Math.random() * 10000000000L));
    }
}
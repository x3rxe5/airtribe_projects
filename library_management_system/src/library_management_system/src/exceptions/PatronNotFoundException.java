package library_management_system.src.exceptions;

public class PatronNotFoundException extends LibraryException {
    public PatronNotFoundException(String patronId) {
        super("Patron with ID " + patronId + " not found");
    }
}
